package quick.pager.shop.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import quick.pager.shop.constants.IConsts;
import quick.pager.shop.goods.constants.GoodsRedisKeys;
import quick.pager.shop.goods.dto.GoodsSkuDTO;
import quick.pager.shop.goods.dto.UploadDTO;
import quick.pager.shop.goods.enums.GoodsPublishStatusEnum;
import quick.pager.shop.goods.response.sku.AppGoodsSkuResponse;
import quick.pager.shop.mapper.GoodsClassMapper;
import quick.pager.shop.mapper.GoodsMapper;
import quick.pager.shop.mapper.GoodsSkuImageMapper;
import quick.pager.shop.mapper.GoodsSkuMapper;
import quick.pager.shop.mapper.GoodsSpuMapper;
import quick.pager.shop.model.Goods;
import quick.pager.shop.model.GoodsClass;
import quick.pager.shop.model.GoodsSku;
import quick.pager.shop.model.GoodsSkuImage;
import quick.pager.shop.model.GoodsSpu;
import quick.pager.shop.service.AppClassificationService;
import quick.pager.shop.user.response.CommonResponse;
import quick.pager.shop.user.response.Response;

@Service
public class AppClassificationServiceImpl implements AppClassificationService {

  @Autowired
  private GoodsClassMapper goodsClassMapper;
  @Autowired
  private GoodsSpuMapper goodsSpuMapper;
  @Autowired
  private GoodsMapper goodsMapper;
  @Autowired
  private GoodsSkuMapper goodsSkuMapper;
  @Autowired
  private GoodsSkuImageMapper goodsSkuImageMapper;
  @Autowired
  private RedisTemplate<String, Object> redisTemplate;


  @Override
  public Response<List<CommonResponse>> spus() {

    final String key = GoodsRedisKeys.REDIS_SPU_PREFIX;

    Boolean hasKey = redisTemplate.hasKey(key);
    ListOperations<String, Object> opsForList = redisTemplate.opsForList();

    if (Objects.nonNull(hasKey) && hasKey) {
      Long size = opsForList.size(key);
      if (Objects.nonNull(size)) {
        List<Object> result = opsForList.range(key, 0L, size);

        if (CollectionUtils.isNotEmpty(result)) {
          return Response.toResponse(result.stream().map(item -> (CommonResponse) item).collect(Collectors.toList()));
        }
      }
    }

    List<GoodsSpu> goodsSpus = goodsSpuMapper.selectList(new LambdaQueryWrapper<GoodsSpu>().orderByAsc(GoodsSpu::getSequence));
    // ????????????????????????
    List<CommonResponse> list = goodsSpus.stream().map(item -> {
      CommonResponse response = new CommonResponse();
      response.setId(item.getId());
      response.setName(item.getSpuName());
      response.setIcon(item.getSpuImage());
      // ????????????
      opsForList.rightPush(key, response);
      return response;
    }).collect(Collectors.toList());

    return Response.toResponse(list);
  }

  @Override
  public Response<List<AppGoodsSkuResponse>> classification(final Long spuId) {

    final String key = GoodsRedisKeys.REDIS_CLASSIFICATION_PREFIX;

    HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();

    // ??????????????????
    if (opsForHash.hasKey(key, spuId)) {
      List<AppGoodsSkuResponse> result = (List<AppGoodsSkuResponse>) opsForHash.get(key, spuId);
      if (CollectionUtils.isNotEmpty(result)) {
        return Response.toResponse(result);
      }
    }

    List<GoodsClass> goodsClasses = goodsClassMapper.selectList(new LambdaQueryWrapper<GoodsClass>()
        .eq(GoodsClass::getSpuId, spuId)
        .orderByAsc(GoodsClass::getSequence)
        .select(GoodsClass::getId, GoodsClass::getClassName)
    );
    // ??????????????????????????????
    if (CollectionUtils.isEmpty(goodsClasses)) {
      return Response.toResponse();
    }

    // ????????????????????????
    List<AppGoodsSkuResponse> list = goodsClasses.stream().map(item -> {
      AppGoodsSkuResponse response = new AppGoodsSkuResponse();
      response.setId(item.getId());
      response.setName(item.getClassName());
      // ????????????
      LambdaQueryWrapper<Goods> wrapper = new LambdaQueryWrapper<Goods>()
          .eq(Goods::getPublishStatus, GoodsPublishStatusEnum.PASS.getCode())
          .eq(Goods::getGoodsClassId, item.getId());
      wrapper.le(Goods::getBeginTime, LocalDate.now());
      wrapper.ge(Goods::getEndTime, LocalDate.now());
      List<Goods> goods = this.goodsMapper.selectList(wrapper);

      if (CollectionUtils.isNotEmpty(goods)) {

        List<Long> goodsIds = goods.stream().map(Goods::getId).collect(Collectors.toList());

        // ??????sku
        List<GoodsSku> goodsSkus = this.goodsSkuMapper.selectList(new LambdaQueryWrapper<GoodsSku>()
            .eq(GoodsSku::getState, Boolean.TRUE)
            .in(GoodsSku::getGoodsId, goodsIds));

        // ??????sku??????
        List<GoodsSkuImage> goodsSkuImages = this.goodsSkuImageMapper.selectList(new LambdaQueryWrapper<GoodsSkuImage>()
            .in(GoodsSkuImage::getGoodsId, goodsIds)
            .select(GoodsSkuImage::getSkuId, GoodsSkuImage::getImages));
        // ??????sku????????????
        Map<Long, List<GoodsSkuImage>> goodsSkuImageMap = goodsSkuImages.stream().collect(Collectors.groupingBy(GoodsSkuImage::getSkuId));

        response.setSkus(goodsSkus.stream().map(sku -> this.convert(sku, goodsSkuImageMap.get(sku.getId()), item.getId())).collect(Collectors.toList()));
      }

      return response;
    }).collect(Collectors.toList());


    // ????????????
    opsForHash.put(key, spuId, list);

    return Response.toResponse(list);
  }

  /**
   * ????????????
   *
   * @param sku            sku??????
   * @param goodsSkuImages sku??????
   * @param goodsClassId   sku??????
   * @return ????????????
   */
  private GoodsSkuDTO convert(final GoodsSku sku, final List<GoodsSkuImage> goodsSkuImages, final Long goodsClassId) {

    String skuImage = null;
    // ??????????????????
    if (CollectionUtils.isNotEmpty(goodsSkuImages)) {
      GoodsSkuImage goodsSkuImage = goodsSkuImages.get(IConsts.ZERO);
      String images = goodsSkuImage.getImages();
      List<UploadDTO> uploadDTOS = JSON.parseArray(images, UploadDTO.class);
      skuImage = uploadDTOS.get(IConsts.ZERO).getUrl();
    }

    return GoodsSkuDTO.builder()
        .skuId(sku.getId())
        .goodsClassId(goodsClassId)
        .skuName(sku.getSkuName())
        .description(sku.getDescription())
        .skuAmount(sku.getSkuAmount())
        .discountAmount(sku.getDiscountAmount())
        .skuImage(skuImage)
        .build();
  }


}

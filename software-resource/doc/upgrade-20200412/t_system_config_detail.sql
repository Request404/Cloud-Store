/*
 Navicat Premium Data Transfer

 Source Server         : prod
 Source Server Type    : MySQL
 Source Server Version : 50646
 Source Host           : 192.168.19.128
 Source Database       : pager_platform

 Target Server Type    : MySQL
 Target Server Version : 50646
 File Encoding         : utf-8

 Date: 04/12/2020 22:04:07 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `t_system_config_detail`
-- ----------------------------
DROP TABLE IF EXISTS `t_system_config_detail`;
CREATE TABLE `t_system_config_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `config_name` varchar(63) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '配置项名称',
  `config_type` varchar(127) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '配置项类型',
  `config_value` varchar(127) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '配置项值',
  `config_key` varchar(31) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '配置项类型值',
  `config_status` bit(1) DEFAULT NULL COMMENT '0 启用 1 禁用',
  `create_user` varchar(63) COLLATE utf8mb4_bin DEFAULT NULL,
  `update_user` varchar(63) COLLATE utf8mb4_bin DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `delete_status` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
--  Records of `t_system_config_detail`
-- ----------------------------
BEGIN;
INSERT INTO `t_system_config_detail` VALUES ('1', '待付款', 'BS001', 'BS001', 'order_status', b'0', 'admin', 'admin', '2020-04-11 19:32:47', '2020-04-11 19:33:35', b'0'), ('2', '待发货', 'BS002', 'BS002', 'order_status', b'0', 'admin', 'admin', '2020-04-11 20:06:11', '2020-04-11 20:06:37', b'0'), ('3', '已发货', 'BS003', 'BS003', 'order_status', b'0', 'admin', 'admin', '2020-04-11 20:25:20', null, b'0'), ('4', '待收获', 'BS004', 'BS004', 'order_status', b'0', 'admin', 'admin', '2020-04-11 20:25:41', null, b'0'), ('5', '已签收', 'BS005', 'BS005', 'order_status', b'0', 'admin', 'admin', '2020-04-11 20:26:09', null, b'0'), ('6', '待评价', 'BS006', 'BS006', 'order_status', b'0', 'admin', 'admin', '2020-04-11 20:26:33', null, b'0'), ('7', '待自提', 'BS007', 'BS007', 'order_status', b'0', 'admin', 'admin', '2020-04-11 20:26:59', null, b'0'), ('8', '已完成', 'BS008', 'BS008', 'order_status', b'0', 'admin', 'admin', '2020-04-11 20:27:23', null, b'0'), ('9', '已取消', 'BS009', 'BS009', 'order_status', b'0', 'admin', 'admin', '2020-04-11 20:27:42', null, b'0'), ('10', '退款中', 'BS010', 'BS010', 'order_status', b'0', 'admin', 'admin', '2020-04-11 20:28:08', null, b'0'), ('11', '已退款', 'BS011', 'BS011', 'order_status', b'0', 'admin', 'admin', '2020-04-11 20:28:42', null, b'0'), ('12', '退货中', 'BS012', 'BS012', 'order_status', b'0', 'admin', 'admin', '2020-04-11 20:29:05', null, b'0'), ('13', '已关闭', 'BS013', 'BS013', 'order_status', b'0', 'admin', 'admin', '2020-04-11 20:29:25', null, b'0'), ('14', '专区订单', 'SPECIAL_ORDER', '1', 'order_type', b'0', 'admin', 'admin', '2020-04-11 20:31:14', null, b'0'), ('15', '普通订单', 'NORMAL_ORDER', '2', 'order_type', b'0', 'admin', 'admin', '2020-04-11 20:32:12', null, b'0'), ('16', '自提订单', 'SELF_ORDER', '3', 'order_type', b'0', 'admin', 'admin', '2020-04-11 20:32:43', null, b'0'), ('17', '秒杀订单', 'SEC_KILL_ORDER', '4', 'order_type', b'0', 'admin', 'admin', '2020-04-11 20:33:05', null, b'0'), ('18', '积分订单', 'INTEGRAL_ORDER', '5', 'order_type', b'0', 'admin', 'admin', '2020-04-11 20:33:28', null, b'0'), ('19', '普通商品', 'NORMAL_GOODS', '1', 'goods_type', b'0', 'admin', 'admin', '2020-04-11 20:36:00', null, b'0'), ('20', '特价商品', 'SPECIAL_GOODS', '2', 'goods_type', b'0', 'admin', 'admin', '2020-04-11 20:36:18', null, b'0'), ('21', '拼团商品', 'ASSEMBLE_GOODS', '3', 'goods_type', b'0', 'admin', 'admin', '2020-04-11 20:37:38', null, b'0'), ('22', '秒杀商品', 'SEC_KILL_GOODS', '4', 'goods_type', b'0', 'admin', 'admin', '2020-04-11 20:37:59', null, b'0'), ('23', '满赠换购商品', 'EXCHANGE_GOODS', '5', 'goods_type', b'0', 'admin', 'admin', '2020-04-11 20:38:18', null, b'0'), ('24', '营销模块', 'module_activity', 'module_activity', 'shop_module', b'0', 'admin', 'admin', '2020-04-11 20:43:24', null, b'0'), ('25', '商品模块', 'module_goods', 'module_goods', 'shop_module', b'0', 'admin', 'admin', '2020-04-11 20:43:49', null, b'0'), ('26', '订单模块', 'module_order', 'module_order', 'shop_module', b'0', 'admin', 'admin', '2020-04-11 20:44:07', null, b'0'), ('27', '商户模块', 'module_seller', 'module_seller', 'shop_module', b'0', 'admin', 'admin', '2020-04-11 20:44:24', null, b'0'), ('28', '用户模块', 'module_user', 'module_user', 'shop_module', b'0', 'admin', 'admin', '2020-04-11 20:44:41', null, b'0'), ('29', '上架', 'UPPER_SHELF_GOODS', '1', 'goods_status', b'0', 'admin', 'admin', '2020-04-11 20:45:50', null, b'0'), ('30', '下架', 'LOWER_SHELF_GOODS', '2', 'goods_status', b'0', 'admin', 'admin', '2020-04-11 20:46:05', null, b'0'), ('31', '待审核', 'PENDING_REVIEW_GOODS', '3', 'goods_status', b'0', 'admin', 'admin', '2020-04-11 20:47:48', null, b'0'), ('32', '系统管理', 'module_admin', 'module_admin', 'shop_module', b'0', 'admin', 'admin', '2020-04-11 20:49:38', null, b'0'), ('33', '优惠券', 'COUPON', '1', 'offer_type', b'0', 'admin', 'admin', '2020-04-12 19:21:14', null, b'0'), ('34', '折扣券', 'DISCOUNT_COUPON', '2', 'offer_type', b'0', 'admin', 'admin', '2020-04-12 19:21:51', null, b'0'), ('35', '首页', 'bannerType', 'home', 'banner_type', b'0', 'admin', 'admin', '2020-04-12 20:29:15', null, b'0'), ('36', '积分商场', 'bannerType', 'integralShop', 'banner_type', b'0', 'admin', 'admin', '2020-04-12 20:29:51', null, b'0'), ('37', '分类', 'bannerType', 'classification', 'banner_type', b'0', 'admin', 'admin', '2020-04-12 20:30:10', null, b'0'), ('38', 'QQ渠道', 'shareChannel', 'qq', 'share_channel', b'0', 'admin', 'admin', '2020-04-12 20:31:45', null, b'0'), ('39', '微信渠道', 'shareChannel', 'wechat', 'share_channel', b'0', 'admin', 'admin', '2020-04-12 20:32:13', null, b'0'), ('40', '微信朋友圈渠道', 'shareChannel', 'wechatFriends', 'share_channel', b'0', 'admin', 'admin', '2020-04-12 20:32:39', null, b'0');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

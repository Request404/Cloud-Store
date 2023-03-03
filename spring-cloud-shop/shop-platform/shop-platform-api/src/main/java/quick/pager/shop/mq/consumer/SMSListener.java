package quick.pager.shop.mq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import quick.pager.shop.mq.PlatformMQ;

/**
 * 发送短信消息监听器
 *
 * @author request404
 */
@Component
@Slf4j
public class SMSListener {

  @StreamListener(target = PlatformMQ.PLATFORM_SEND_SMS_INPUT)
  public void process(@Payload String payload) {
    log.info("发送短信接受消息 payload = {}", payload);
    // fixme 对接第三方短信服务
  }
}

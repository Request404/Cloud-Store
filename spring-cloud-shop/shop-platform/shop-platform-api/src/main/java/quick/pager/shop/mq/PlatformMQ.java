package quick.pager.shop.mq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * 消息队列
 *
 * @author request404
 */
public interface PlatformMQ {

  /**
   * 发送短信队列
   */
  String PLATFORM_SEND_SMS_OUTPUT = "platform-send-sms-output";
  /**
   * 监听发送短信队列
   */
  String PLATFORM_SEND_SMS_INPUT = "platform-send-sms-input";

  @Output(PLATFORM_SEND_SMS_OUTPUT)
  MessageChannel sendSMS();

  @Input(PLATFORM_SEND_SMS_INPUT)
  SubscribableChannel receiveSMS();
}

package quick.pager.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author request404
 */
@SpringCloudApplication
@EnableFeignClients
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class Manage {

  public static void main(String[] args) {
    SpringApplication.run(Manage.class, args);
  }
}

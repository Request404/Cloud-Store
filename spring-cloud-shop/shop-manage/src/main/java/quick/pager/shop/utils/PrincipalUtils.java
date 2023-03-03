package quick.pager.shop.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 获取Spring Security Principal
 *
 * @author request404
 */
public class PrincipalUtils {

  public static Authentication getPrincipal() {
    return SecurityContextHolder.getContext().getAuthentication();
  }
}

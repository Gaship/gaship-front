package shop.gaship.gashipfront.aspect.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Jwt Access Token의 만료기한을 체크하여 만료된 토큰일시 재요청 하는 어노테이션.
 *
 * @author : 조재철
 * @since 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JwtExpiredCheck {

}

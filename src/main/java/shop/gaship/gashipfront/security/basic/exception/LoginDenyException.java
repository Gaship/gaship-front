package shop.gaship.gashipfront.security.basic.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 일반 로그인 시 소셜로그인을 시도 할 때 로그인 요청을 불허가하는 예외입니다.
 *
 * @author 김민수
 * @since 1.0
 */
public class LoginDenyException extends AuthenticationException {

    public LoginDenyException(String explanation) {
        super(explanation);
    }
}

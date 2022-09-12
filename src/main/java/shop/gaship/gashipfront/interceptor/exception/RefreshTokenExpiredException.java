package shop.gaship.gashipfront.interceptor.exception;

/**
 * refreshToken 의 인증기간이 만료되었을때 발생하는 예외 클래스 입니다.
 *
 * @author : 조재철
 * @since 1.0
 */
public class RefreshTokenExpiredException extends RuntimeException {

    public static final String MESSAGE = "refresh 토큰의 인증기간이 만료되었습니다.";

    public RefreshTokenExpiredException() {
        super(MESSAGE);
    }
}

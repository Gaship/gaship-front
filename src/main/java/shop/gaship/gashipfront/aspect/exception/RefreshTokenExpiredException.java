package shop.gaship.gashipfront.aspect.exception;

/**
 * @author : 조재철
 * @since 1.0
 */
public class RefreshTokenExpiredException extends RuntimeException {

    public static final String MESSAGE = "refresh 토큰의 인증기간이 만료되었습니다.";

    public RefreshTokenExpiredException() {
        super(MESSAGE);
    }
}

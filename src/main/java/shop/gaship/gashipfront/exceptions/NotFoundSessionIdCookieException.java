package shop.gaship.gashipfront.exceptions;

/**
 * @author : 조재철
 * @since 1.0
 */
public class NotFoundSessionIdCookieException extends RuntimeException {

    public static final String MESSAGE = "해당 세션 쿠키를 찾을 수 없습니다.";

    public NotFoundSessionIdCookieException() {
        super(MESSAGE);
    }
}

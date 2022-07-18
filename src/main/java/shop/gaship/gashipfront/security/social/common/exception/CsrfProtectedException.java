package shop.gaship.gashipfront.security.social.common.exception;

/**
 * csrf 방어를위해 생산한 state 값이 서버의 값과 다른경우 발생시키는 예외입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
public class CsrfProtectedException extends RuntimeException {
    /**
     * Instantiates a new Csrf protected exception.
     *
     * @param message the message
     */
    public CsrfProtectedException(String message) {
        super(message);
    }
}

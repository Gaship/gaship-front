package shop.gaship.gashipfront.security.common.exception;

/**
 * csrf 방어를위해 생산한 state 값이 서버의 값과 다른경우 발생시키는 예외입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
public class CsrfProtectedException extends RuntimeException {
    /**
     * CsrfProtectedException클래스를 객체화시키기 위한 생성자입니다.
     *
     * @param message 예외 메세지를 저장하고있는 변수입니다.
     */
    public CsrfProtectedException(String message) {
        super(message);
    }
}

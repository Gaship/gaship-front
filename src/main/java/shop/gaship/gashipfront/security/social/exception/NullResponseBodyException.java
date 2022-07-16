package shop.gaship.gashipfront.security.social.exception;

/**
 * @author : 최겸준
 * @see java.lang.RuntimeException
 * @since 1.0
 */
public class NullResponseBodyException extends RuntimeException {
    public NullResponseBodyException() {
        super("body is null : 서버에서 받아온 ResponseEntity의 body에 값이 없습니다.");
    }
}

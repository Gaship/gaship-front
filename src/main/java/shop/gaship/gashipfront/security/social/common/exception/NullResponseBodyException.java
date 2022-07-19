package shop.gaship.gashipfront.security.social.common.exception;

/**
 * ResponseEntity를 받았을때 body에 ErrorResponse타입이 있는데 값은 null인경우에 발생시키는 예외입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
public class NullResponseBodyException extends RuntimeException {
    /**
     * NullResponseBodyException 클래스를 객체화시키기 위한 생성자입니다. 해당 생성자는 항상 같은 값을 반환하기위해서 message를 파라미터로 받지 않습니다.
     */
    public NullResponseBodyException() {
        super("body is null : 서버에서 받아온 ResponseEntity의 body에 값이 없습니다.");
    }
}

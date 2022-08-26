package shop.gaship.gashipfront.exceptions;

/**
 * session에서 memberNo를 찾아왔을때 어떠한 오류로 인해서 memberNo가 안찾아와졌거나
 * memberNo가 삽입이 되지 않았었던 경우에 발생할 예외입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
public class MemberNotCreationException extends RuntimeException {

    public static final String MESSAGE = "가입되지 않은 회원번호입니다.";

    public MemberNotCreationException() {
        super(MESSAGE);
    }
}

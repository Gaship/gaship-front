package shop.gaship.gashipfront.exceptions;

/**
 * session에서 memberNo를 찾아왔을때 어떠한 오류로 인해서 memberNo가 안찾아와졌거나
 * memberNo가 삽입이 되지 않았었던 경우에 발생할 예외입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
public class MemberNoNotFoundException extends RuntimeException {

    public static final String MESSAGE = "회원 번호가 session에 저장되지 않았거나 해당 번호를 들고오지 못했습니다.";

    public MemberNoNotFoundException() {
        super(MESSAGE);
    }
}

package shop.gaship.gashipfront.member.exception;

/**
 * 회원가입 거부발생시 일어나는 예외입니다.
 *
 * @author 김민수
 * @since 1.0
 */
public class SignUpDenyException extends RuntimeException {
    public SignUpDenyException(String message) {
        super(message);
    }
}

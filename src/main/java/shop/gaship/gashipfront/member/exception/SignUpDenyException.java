package shop.gaship.gashipfront.member.exception;

/**
 * 설명작성란
 *
 * @author 김민수
 * @since 1.0
 */
public class SignUpDenyException extends RuntimeException {
    public SignUpDenyException(String message) {
        super(message);
    }
}

package shop.gaship.gashipfront.member.exception;

/**
 * 이메일 요청시 이메일이 없거나 비었을 때 발생하는 예외입니다.
 *
 * @author 김민수
 * @since 1.0
 */
public class CanNotRequestEmailVerify extends RuntimeException {
    public static final String ERROR_MESSAGE = "이메일을 입력하세요.";
    public CanNotRequestEmailVerify() {
        super(ERROR_MESSAGE);
    }
}

package shop.gaship.gashipfront.security.common.gashipauth.exception;

/**
 * 로그아웃 중 예상치 못한 예외가 발생하였을 시 던지는 예외입니다.
 *
 * @author 김민수
 * @since 1.0
 */
public class LogoutFailureException extends RuntimeException {
    private static final String MESSAGE = "로그아웃 도중에 문제가 발생하였습니다. 잠시 후 시도해주세요";

    public LogoutFailureException() {
        super(MESSAGE);
    }
}

package shop.gaship.gashipfront.member.exception;

/**
 * webclient를 통해서 서버의 응답을 받지 못하고 오류가 발생했을 시 발생시키는 예외입니다.
 *
 * @author : 김민수
 * @since 1.0
 */
public class RequestFailureException extends RuntimeException {
    private static final String MESSAGE = "서버의 요청에 실패했습니다.";

    public RequestFailureException() {
        super(MESSAGE);
    }
}

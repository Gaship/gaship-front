package shop.gaship.gashipfront.member.exception;

/**
 * packageName    : shop.gaship.gashipfront.member.exception <br/>
 * fileName       : RequestFailureException <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/14 <br/>
 * description    : webclient를 통해서 서버의 응답을 받지 못하고 오류가 발생했을 시 발생시키는 예외입니다.<br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/14           김민수               최초 생성                         <br/>
 */
public class RequestFailureException extends RuntimeException {
    private static final String MESSAGE = "서버의 요청에 실패했습니다.";

    public RequestFailureException() {
        super(MESSAGE);
    }
}

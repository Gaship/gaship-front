package shop.gaship.gashipfront.exceptions;

import shop.gaship.gashipfront.message.ErrorResponse;

/**
 * packageName    : shop.gaship.gashipfront.exceptions <br/>
 * fileName       : RequestFailureThrow<br/>
 * author         : 김보민<br/>
 * date           : 2022-07-14<br/>
 * description    : 서버에 보낸 요청이 실패했을때의 예외입니다.<br/>
 * ===========================================================<br/>
 * DATE              AUTHOR             NOTE                  <br/>
 * -----------------------------------------------------------<br/>
 * 2022-07-14        김보민       최초 생성<br/>
 */
public class RequestFailureThrow extends Throwable {
    public RequestFailureThrow(ErrorResponse errorResponse) {
        super(errorResponse.getMessage());
    }
}

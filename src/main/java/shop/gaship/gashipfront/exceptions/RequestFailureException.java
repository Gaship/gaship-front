package shop.gaship.gashipfront.exceptions;

import shop.gaship.gashipfront.message.ErrorResponse;

/**
 * packageName    : shop.gaship.gashipfront.exceptions
 * fileName       : RequestFailureException
 * author         : 김보민
 * date           : 2022-07-14
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-07-14        김보민       최초 생성
 */
public class RequestFailureException extends Throwable {
    public RequestFailureException(ErrorResponse errorResponse) {
        super(errorResponse.getMessage());
    }

    public RequestFailureException(String message) {
        super(message);
    }
}

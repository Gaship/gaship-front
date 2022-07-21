package shop.gaship.gashipfront.message;

import lombok.NoArgsConstructor;

/**
 * packageName    : shop.gaship.gashipshoppingmall.message
 * fileName       : ErrorResponse
 * author         : 김보민
 * date           : 2022-07-14
 * description    : 에러 발생 시 response body
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-07-14        김보민       최초 생성
 */
@NoArgsConstructor
public class ErrorResponse {
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

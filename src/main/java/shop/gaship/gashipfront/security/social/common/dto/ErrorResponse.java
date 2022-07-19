package shop.gaship.gashipfront.security.social.common.dto;

/**
 * api 서버에서 예외가 발생해서 넘어온 경우 body의 값을 받을 dto 입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
public class ErrorResponse {
    private String message;

    /**
     * ErrorResponse 클래스의 생성자입니다.
     *
     * @param message 요청한 API서버로부터 넘어온 예외에 대한 message를 저장하는 변수입니다.
     */
    public ErrorResponse(String message) {
        this.message = message;
    }

    /**
     * getter 입니다.
     *
     * @return String
     */
    public String getMessage() {
        return message;
    }
}

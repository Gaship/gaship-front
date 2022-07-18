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
     * Instantiates a new Error response.
     *
     * @param message the message
     */
    public ErrorResponse(String message) {
        this.message = message;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }
}

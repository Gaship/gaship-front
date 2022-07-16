package shop.gaship.gashipfront.security.social.exception;

/**
 * @author : 최겸준
 * @since 1.0
 */
public class ErrorResponse {
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

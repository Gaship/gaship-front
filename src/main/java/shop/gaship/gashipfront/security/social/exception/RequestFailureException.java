package shop.gaship.gashipfront.security.social.exception;

import org.springframework.http.HttpStatus;

/**
 * @author : 최겸준
 * @see java.lang.RuntimeException
 * @since 1.0
 */
public class RequestFailureException extends RuntimeException {
    private HttpStatus statusCode;

    public RequestFailureException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}

package shop.gaship.gashipfront.security.social.common.exception;

import org.springframework.http.HttpStatus;

/**
 * API 서버에서 예외상황으로 넘어온경우에 발생시킬 예외클래스입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
public class RequestFailureException extends RuntimeException {
    private HttpStatus statusCode;

    public RequestFailureException(String message) {
        super(message);
        this.statusCode = statusCode;
    }

    /**
     * Instantiates a new Request failure exception.
     *
     * @param message    the message
     * @param statusCode the status code
     */
    public RequestFailureException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    /**
     * Gets status code.
     *
     * @return the status code
     */
    public HttpStatus getStatusCode() {
        return statusCode;
    }
}

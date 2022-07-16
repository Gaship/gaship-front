package shop.gaship.gashipfront.security.social.exception;

/**
 * @author : 최겸준
 * @see java.lang.RuntimeException
 * @since 1.0
 */
public class ResponseEntityBodyIsErrorResponseException extends RuntimeException {
    public ResponseEntityBodyIsErrorResponseException(String message) {
        super(message);
    }
}

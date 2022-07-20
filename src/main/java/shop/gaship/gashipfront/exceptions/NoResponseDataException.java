package shop.gaship.gashipfront.exceptions;

/**
 * @author 조재철
 * @since 1.0
 */
public class NoResponseDataException extends RuntimeException {

    /**
     * Instantiates a new No response data exception.
     *
     * @param message the message
     */
    public NoResponseDataException(String message) {
        super(message);
    }
}

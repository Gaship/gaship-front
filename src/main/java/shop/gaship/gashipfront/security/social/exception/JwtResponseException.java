package shop.gaship.gashipfront.security.social.exception;

/**
 * @author : 최겸준
 * @see shop.gaship.
 * @since 1.0
 */
public class JwtResponseException extends RuntimeException {
    public JwtResponseException(String message) {
        super(message);
    }
}

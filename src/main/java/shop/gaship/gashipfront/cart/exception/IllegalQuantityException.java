package shop.gaship.gashipfront.cart.exception;

/**
 * @author 최정우
 * @since 1.0
 */
public class IllegalQuantityException extends RuntimeException {
    public static final String MESSAGE = "해당 상품은 존재하지 않습니다.";

    public IllegalQuantityException() {
        super(MESSAGE);
    }
}

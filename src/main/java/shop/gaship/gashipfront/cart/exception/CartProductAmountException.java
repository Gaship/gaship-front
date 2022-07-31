package shop.gaship.gashipfront.cart.exception;

/**
 * @author 최정우
 * @since 1.0
 */
public class CartProductAmountException extends Exception {
    public static final String MESSAGE = "장바구니에 담긴 상품의 갯수는 0 이하가 될 수 없습니다.";

    public CartProductAmountException() {
        super(MESSAGE);
    }
}

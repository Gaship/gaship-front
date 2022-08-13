package shop.gaship.gashipfront.cart.exception;

/**
 * 특정 상품의 수량이 10인데 수량 + 버튼을 누른 경우.
 *
 * @author 최정우
 * @since 1.0
 */
public class CartProductAmountException extends Exception {
    public static final String MESSAGE = "상품의 수량은 1~10개 사이로 선택해주십시오.";

    public CartProductAmountException() {
        super(MESSAGE);
    }
}

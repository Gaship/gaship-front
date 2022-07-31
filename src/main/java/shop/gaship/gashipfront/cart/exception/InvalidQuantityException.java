package shop.gaship.gashipfront.cart.exception;

/**
 * @author 최정우
 * @since 1.0
 */
public class InvalidQuantityException extends Exception {
    public static final String MESSAGE = "주문하려는 상품의 수량을 0~10 사이로 설정해야합니다.";

    public InvalidQuantityException() {
        super(MESSAGE);
    }
}

package shop.gaship.gashipfront.cart.exception;

/**
 * 장바구니에 담은 상품의 종류가 10가지가 넘어갈 때 발생하는 에러 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
public class CartMaxLimitException extends RuntimeException {
    public static final String MESSAGE = "장바구니에 담을 수 있는 상품 종류는 10개를 넘을 수 없습니다.";

    public CartMaxLimitException() {
        super(MESSAGE);
    }
}

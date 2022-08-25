package shop.gaship.gashipfront.cart.exception;

/**
 * 비회원 때 담은 상품이 장바구니의 총량을 넘어 담기지 않았습니다.
 *
 * @author 최정우
 * @since 1.0
 */
public class CartMergeException extends RuntimeException {
    public static final String MESSAGE = "비회원 때 담은 상품이 제대로 안 담겼습니다.";

    public CartMergeException() {
        super(MESSAGE);
    }
}

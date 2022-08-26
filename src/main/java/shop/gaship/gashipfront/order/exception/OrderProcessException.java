package shop.gaship.gashipfront.order.exception;

/**
 * 주문 요청 과정에서 발생하는 exception 입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
public class OrderProcessException extends RuntimeException {
    public static final String MESSAGE = "주문 요청에 실패하였습니다.";

    public OrderProcessException() {
        super(MESSAGE);
    }
}

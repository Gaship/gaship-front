package shop.gaship.gashipfront.order.exception;

/**
 * 주문 요청 과정에서 쿠폰 처리에 문제가 있을때 발생하는 exception 입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
public class CouponProcessException extends RuntimeException {
    public static final String MESSAGE = "현재 쿠폰 사용이 불가능합니다.";

    public CouponProcessException() {
        super(MESSAGE);
    }
}

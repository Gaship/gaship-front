package shop.gaship.gashipfront.order.exception;

/**
 * 결제 서버상의 issue 로 발생하는 Exception 입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
public class PaymentServerException extends RuntimeException {
    public static final String MESSAGE = "결제 서버에 문제가 발생하였습니다.";

    public PaymentServerException() {
        super(MESSAGE);
    }
}

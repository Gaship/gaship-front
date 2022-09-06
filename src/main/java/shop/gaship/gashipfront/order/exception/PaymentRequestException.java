package shop.gaship.gashipfront.order.exception;

/**
 * 결제 요청이 client 의 요청 이슈로 실패했을 때 발생하는 exception 입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
public class PaymentRequestException extends RuntimeException {
    public static final String MESSAGE = "결제 요청에 실패하였습니다.";

    public PaymentRequestException() {
        super(MESSAGE);
    }
}

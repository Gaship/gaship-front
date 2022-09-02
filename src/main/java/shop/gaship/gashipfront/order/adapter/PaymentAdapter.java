package shop.gaship.gashipfront.order.adapter;

import shop.gaship.gashipfront.order.dto.request.OrderPaymentCancelRequestDto;
import shop.gaship.gashipfront.order.dto.request.PaymentSuccessRequestDto;

/**
 *
 *
 * @author : 김세미
 * @since 1.0
 */
public interface PaymentAdapter {
    void successPayment(PaymentSuccessRequestDto requestDto);

    void cancelPayment(Integer orderNo,
                       OrderPaymentCancelRequestDto orderPaymentCancelRequestDto);
}

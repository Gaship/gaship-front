package shop.gaship.gashipfront.order.service;

import shop.gaship.gashipfront.order.dto.request.OrderCancelRequestDto;
import shop.gaship.gashipfront.order.dto.request.OrderRegisterRequestDto;
import shop.gaship.gashipfront.order.dto.request.PaymentSuccessRequestDto;
import shop.gaship.gashipfront.order.dto.response.OrderResponseDto;

/**
 * 주문 관련 처리를 위한 service interface 입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
public interface OrderService {
    OrderResponseDto processOrder(Integer memberNo, OrderRegisterRequestDto requestDto);

    void successPayment(PaymentSuccessRequestDto requestDto);

    void cancelOrder(Integer orderNo,
                     OrderCancelRequestDto requestDto, Integer memberNo);
}

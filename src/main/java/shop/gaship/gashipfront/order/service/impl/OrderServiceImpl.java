package shop.gaship.gashipfront.order.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.order.adapter.OrderAdapter;
import shop.gaship.gashipfront.order.adapter.PaymentAdapter;
import shop.gaship.gashipfront.order.dto.request.*;
import shop.gaship.gashipfront.order.dto.response.OrderResponseDto;
import shop.gaship.gashipfront.order.service.OrderService;

import java.util.List;

/**
 * 주문 관련 처리를 위한 service 구현체 입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderAdapter orderAdapter;
    private final PaymentAdapter paymentAdapter;


    @Override
    public OrderResponseDto processOrder(Integer memberNo, OrderRegisterRequestDto requestDto) {
        requestDto.setMemberNo(memberNo);


        return orderAdapter.doOrder(requestDto);
    }

    @Override
    public void successPayment(PaymentSuccessRequestDto requestDto) {
        paymentAdapter.successPayment(requestDto);
    }

    @Override
    public void cancelOrder(Integer orderNo,
                            OrderCancelRequestDto requestDto, Integer memberNo) {
        CancelOrderInfo cancelOrderInfo =
                new CancelOrderInfo(
                        requestDto.getCancelOrderProductNo(),
                        requestDto.getCancelAmount());

        OrderPaymentCancelRequestDto orderPaymentCancelRequestDto =
                new OrderPaymentCancelRequestDto(
                        requestDto.getCancelReason(), List.of(cancelOrderInfo));

        paymentAdapter.cancelPayment(orderNo, orderPaymentCancelRequestDto);
    }
}

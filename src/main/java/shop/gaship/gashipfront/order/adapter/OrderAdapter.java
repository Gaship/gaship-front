package shop.gaship.gashipfront.order.adapter;

import shop.gaship.gashipfront.order.dto.request.OrderRegisterRequestDto;
import shop.gaship.gashipfront.order.dto.response.OrderResponseDto;

/**
 * 주문 요청을 위한 adapter interface 입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
public interface OrderAdapter {
    OrderResponseDto doOrder(OrderRegisterRequestDto requestDto);
}

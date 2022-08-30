package shop.gaship.gashipfront.order.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.order.adapter.OrderAdapter;
import shop.gaship.gashipfront.order.dto.request.OrderRegisterRequestDto;
import shop.gaship.gashipfront.order.dto.response.OrderResponseDto;
import shop.gaship.gashipfront.order.service.OrderService;

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


    @Override
    public OrderResponseDto processOrder(Integer memberNo, OrderRegisterRequestDto requestDto) {
        requestDto.setMemberNo(memberNo);
        OrderResponseDto responseDto = orderAdapter.doOrder(requestDto);

        return null;
    }
}

package shop.gaship.gashipfront.order.adapter;

import shop.gaship.gashipfront.order.dto.request.OrderRegisterRequestDto;
import shop.gaship.gashipfront.order.dto.request.SuccessOrderRequestDto;
import shop.gaship.gashipfront.order.dto.response.OrderResponseDto;

/**
 * 주문 요청을 위한 adapter interface 입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
public interface OrderAdapter {
    /**
     * shopping mall 서버에 주문 등록 요청을 보내는 메서드입니다.
     *
     * @param requestDto 주문 등록 요청을 위한 요청 Dto 입니다.
     * @return 주문 등록 요청에 대한 응답 dto 를 반환합니다.
     * @author 김세미
     */
    OrderResponseDto doOrder(OrderRegisterRequestDto requestDto);

    void orderSuccess(SuccessOrderRequestDto requestDto);
}

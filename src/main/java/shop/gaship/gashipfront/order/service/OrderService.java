package shop.gaship.gashipfront.order.service;

import shop.gaship.gashipfront.order.dto.request.OrderCancelRequestDto;
import shop.gaship.gashipfront.order.dto.request.OrderRegisterRequestDto;
import shop.gaship.gashipfront.order.dto.request.PaymentSuccessRequestDto;
import shop.gaship.gashipfront.order.dto.request.SuccessOrderRequestDto;
import shop.gaship.gashipfront.order.dto.response.OrderResponseDto;

/**
 * 주문 관련 처리를 위한 service interface 입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
public interface OrderService {
    /**
     * 결제 요청을 보내기 전에 주문 등록을 처리하기 위한 메서드입니다.
     *
     * @param memberNo 주문하는 회원의 식별번호입니다.
     * @param requestDto 주문을 등록하기 위한 상품 정보와 금액 정보를 갖는 요청 dto 입니다.
     * @return orderResponseDto 주문 등록 요청에 대한 응답 dto 입니다.
     * @author 김세미
     */
    OrderResponseDto processOrder(Integer memberNo, OrderRegisterRequestDto requestDto);

    void successPayment(PaymentSuccessRequestDto requestDto);

    void cancelOrder(Integer orderNo,
                     OrderCancelRequestDto requestDto, Integer memberNo);

    /**
     * 결제는 완료되었지만 서버상의 이슈로 인해 주문 완료처리가 정상적으로 동작하지 않았을 경우
     * 해당 주문의 완료처리를 하는 메서드입니다.
     *
     * @param requestDto 주문 완료 요청 dto 입니다.
     */
    void successOrder(SuccessOrderRequestDto requestDto);
}

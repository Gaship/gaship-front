package shop.gaship.gashipfront.order.dto.request;

import lombok.Builder;
import lombok.Getter;

/**
 * 결제 승인 완료 후 주문 성공 처리 요청을 위한 dto 입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
@Builder
public class SuccessOrderRequestDto {
    String paymentKey;
    Integer orderNo;
}

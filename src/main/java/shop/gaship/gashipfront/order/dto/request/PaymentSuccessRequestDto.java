package shop.gaship.gashipfront.order.dto.request;

import lombok.Builder;
import lombok.Getter;

/**
 * 결제 성공 승인 처리 요청 dto 입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
@Builder
public class PaymentSuccessRequestDto {
    private String paymentKey;
    private String orderId;
    private Long amount;
    private String provider;
}

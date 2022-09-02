package shop.gaship.gashipfront.order.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * 주문 취소 및 결제 취소 요청 dto 입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderPaymentCancelRequestDto {
    private String cancelReason;
    private List<CancelOrderInfo> cancelOrderInfos;
}

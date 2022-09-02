package shop.gaship.gashipfront.order.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문 취소 요청에 포함된 주문 상품에 대한 정보입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CancelOrderInfo {
    private Integer cancelOrderProductNo;
    private Long cancelAmount;
}

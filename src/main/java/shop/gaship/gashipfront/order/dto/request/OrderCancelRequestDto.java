package shop.gaship.gashipfront.order.dto.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 주문 취소 요청 dto 입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
@Setter
public class OrderCancelRequestDto {
    private String cancelReason;
    private Integer cancelOrderProductNo;
    private Long cancelAmount;
}

package shop.gaship.gashipfront.order.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문 요청에 대한 응답 dto 입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
@NoArgsConstructor
public class OrderResponseDto {
    private Long amount; // 실제값은 totalAmount
    private Integer orderId; // 실제값은 orderNo
    private String orderName;
    private String customerName;
}

package shop.gaship.gashipfront.order.dto.request;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 주문 요청시 주문 상품에 대한 상세 정보입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class OrderProductSpecificDto {
    private Integer productNo;
    private Long amount;
    private Integer couponNo;
    private Long couponAmount;
    private LocalDate hopeDate;
}

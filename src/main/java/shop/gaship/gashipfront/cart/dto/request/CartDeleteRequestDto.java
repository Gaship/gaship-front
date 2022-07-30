package shop.gaship.gashipfront.cart.dto.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author 최정우
 * @since 1.0
 */
@Data
@Builder
public class CartDeleteRequestDto {
    @Min(value = 0, message = "장바구니정보가 올바르지 않습니다.")
    private final String cartId;

    @Min(value = 0, message = "상품정보가 올바르지 않습니다.")
    private final Integer productId;

    @Min(value = 0, message = "가십케어 플러스 기간이 잘못 입력 되었습니다.")
    @Max(value = 10, message = "가십케어 플러스 기간은 최대 10년을 입니다.")
    private final Integer carePeriod;
}

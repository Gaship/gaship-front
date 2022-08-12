package shop.gaship.gashipfront.cart.dto.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author 최정우
 * @since 1.0
 */
@Getter
@Builder
public class CartProductQuantityUpDownRequestDto {
    @Min(value = 1, message = "회원 id 는 1 이상이어야합니다.")
    private Integer userId;

    @Min(value = 1, message = "상품 id 는 1 이상이어야합니다.")
    private Integer productId;
}

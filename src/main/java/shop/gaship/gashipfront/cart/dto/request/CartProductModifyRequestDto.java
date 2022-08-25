package shop.gaship.gashipfront.cart.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;


/**
 * 장바구니에 담긴 물품의 수량변경정보를 담은 dto 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@Getter
@Setter
public class CartProductModifyRequestDto {
    @Min(value = 1, message = "상품 id 는 1 이상이어야합니다.")
    private Integer productId;

    @Min(value = 1, message = "상품은 한개 이상부터 주문가응합니다.")
    @Max(value = 10, message = "상품은 한 번에 열개 까지 구매가능합니다.")
    private Integer quantity;
}

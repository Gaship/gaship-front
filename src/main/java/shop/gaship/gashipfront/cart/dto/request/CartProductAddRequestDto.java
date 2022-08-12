package shop.gaship.gashipfront.cart.dto.request;

import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 장바구니에 상품을 추가하는 정보를 담은 dto 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@Getter
public class CartProductAddRequestDto {
    @Min(value = 1, message = "회원 id 는 1 이상이어야합니다.")
    private Integer userId;

    @Min(value = 1, message = "상품 id 는 1 이상이어야합니다.")
    private Integer productId;

    @Min(value = 1, message = "상품은 한개 이상부터 주문가응합니다.")
    @Max(value = 10, message = "상품은 한 번에 열개 까지 구매가능합니다.")
    private Integer quantity;
}

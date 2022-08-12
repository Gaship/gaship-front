package shop.gaship.gashipfront.cart.dto.request;

import lombok.Getter;

import javax.validation.constraints.Min;

/**
 * 장바구니에 담긴 물건을 삭제하는 정보를 담은 dto 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@Getter
public class CartProductDeleteRequestDto {
    @Min(value = 1, message = "회원 id 는 1 이상이어야합니다.")
    private Integer userId;

    @Min(value = 1, message = "상품 id 는 1 이상이어야합니다.")
    private Integer productId;
}

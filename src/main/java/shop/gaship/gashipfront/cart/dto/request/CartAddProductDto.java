package shop.gaship.gashipfront.cart.dto.request;

import lombok.Getter;

/**
 * @author 최정우
 * @since 1.0
 */
@Getter
public class CartAddProductDto {
    private final Integer userId;
    private final Integer productId;
    private final String productColor;
    private final Integer productQuantity;

    public CartAddProductDto(Integer userId, Integer productId, String productColor, Integer productQuantity) {
        this.userId = userId;
        this.productId = productId;
        this.productColor = productColor;
        this.productQuantity = productQuantity;
    }
}

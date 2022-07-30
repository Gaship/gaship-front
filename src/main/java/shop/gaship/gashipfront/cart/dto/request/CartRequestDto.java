package shop.gaship.gashipfront.cart.dto.request;

import lombok.Data;

/**
 * @author 최정우
 * @since 1.0
 */
@Data
public class CartRequestDto {
    private final String cartId;
    private final Integer productId;
    private final Integer carePeriod;
    private final Integer quantity;
}

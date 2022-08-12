package shop.gaship.gashipfront.cart.dummy;

import lombok.EqualsAndHashCode;
import shop.gaship.gashipfront.cart.dto.request.CartProductDeleteRequestDto;
import shop.gaship.gashipfront.cart.dto.request.CartProductModifyRequestDto;
import shop.gaship.gashipfront.cart.dto.request.CartProductQuantityUpDownRequestDto;

/**
 * @author 최정우
 * @since 1.0
 */
@EqualsAndHashCode
public class CartDummy {
    public static CartRequestDto CartRequestDtoDummy(Integer productId, Integer carePeriod, Integer quantity) {
        return CartRequestDto.builder()
                .productId(productId)
                .carePeriod(carePeriod)
                .quantity(quantity)
                .build();
    }

    public static CartProductModifyRequestDto CartModifyRequestDtoDummy(Integer productId, Integer carePeriod, Integer quantity) {
        return CartProductModifyRequestDto.builder()
                .productId(productId)
                .carePeriod(carePeriod)
                .quantity(quantity)
                .build();
    }

    public static CartProductQuantityUpDownRequestDto CartProductQuantityUpDownRequestDtoDummy(Integer productId, Integer carePeriod) {
        return CartProductQuantityUpDownRequestDto.builder()
                .productId(productId)
                .carePeriod(carePeriod)
                .build();
    }

    public static CartProductDeleteRequestDto CartDeleteDtoDummy(Integer productId, Integer carePeriod) {
        return CartProductDeleteRequestDto.builder()
                .productId(productId)
                .carePeriod(carePeriod)
                .build();
    }
}

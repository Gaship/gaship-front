package shop.gaship.gashipfront.cart.dummy;

import lombok.EqualsAndHashCode;
import shop.gaship.gashipfront.cart.dto.request.CartDeleteRequestDto;
import shop.gaship.gashipfront.cart.dto.request.CartModifyRequestDto;
import shop.gaship.gashipfront.cart.dto.request.CartProductQuantityUpDownRequestDto;
import shop.gaship.gashipfront.cart.dto.request.CartRequestDto;

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

    public static CartModifyRequestDto CartModifyRequestDtoDummy(Integer productId, Integer carePeriod, Integer quantity) {
        return CartModifyRequestDto.builder()
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

    public static CartDeleteRequestDto CartDeleteDtoDummy(Integer productId, Integer carePeriod) {
        return CartDeleteRequestDto.builder()
                .productId(productId)
                .carePeriod(carePeriod)
                .build();
    }
}

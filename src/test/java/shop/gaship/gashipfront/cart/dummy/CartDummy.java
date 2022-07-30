package shop.gaship.gashipfront.cart.dummy;

import shop.gaship.gashipfront.cart.dto.request.CartDeleteRequestDto;
import shop.gaship.gashipfront.cart.dto.request.CartModifyRequestDto;
import shop.gaship.gashipfront.cart.dto.request.CartProductQuantityUpDownRequestDto;
import shop.gaship.gashipfront.cart.dto.request.CartRequestDto;

/**
 * @author 최정우
 * @since 1.0
 */
public class CartDummy {

    public static CartRequestDto CartRequestDtoDummy(String cartKey,Integer productId, Integer carePeriod,Integer quantity){
        return CartRequestDto.builder()
                .cartId(cartKey)
                .productId(productId)
                .carePeriod(carePeriod)
                .quantity(quantity)
                .build();
    }

    public static  CartModifyRequestDto CartModifyRequestDtoDummy(String cartKey,Integer productId, Integer carePeriod,Integer quantity){
        return CartModifyRequestDto.builder()
                .cartId(cartKey)
                .productId(productId)
                .carePeriod(carePeriod)
                .quantity(quantity)
                .build();
    }

    public static CartProductQuantityUpDownRequestDto CartProductQuantityUpDownRequestDtoDummy(String cartKey, Integer productId, Integer carePeriod){
        return CartProductQuantityUpDownRequestDto.builder()
                .cartId(cartKey)
                .productId(productId)
                .carePeriod(carePeriod)
                .build();
    }

    public static CartDeleteRequestDto CartDeleteDtoDummy(String cartKey, Integer productId, Integer carePeriod){
        return CartDeleteRequestDto.builder()
                .cartId(cartKey)
                .productId(productId)
                .carePeriod(carePeriod)
                .build();
    }
}

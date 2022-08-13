package shop.gaship.gashipfront.cart.dummy;

import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipfront.cart.dto.request.CartProductDeleteRequestDto;
import shop.gaship.gashipfront.cart.dto.request.CartProductModifyRequestDto;

/**
 * @author 최정우
 * @since 1.0
 */
public class CartDummy {
    public static CartProductDeleteRequestDto cartProductDeleteRequestDto(Integer productId) {
        CartProductDeleteRequestDto dto = new CartProductDeleteRequestDto();
        ReflectionTestUtils.setField(dto, "productId", productId);

        return dto;
    }

    public static CartProductModifyRequestDto cartProductModifyRequestDto(Integer productId, Integer quantity) {
        CartProductModifyRequestDto dto = new CartProductModifyRequestDto();
        ReflectionTestUtils.setField(dto, "productId", productId);
        ReflectionTestUtils.setField(dto, "quantity", quantity);

        return dto;
    }
}

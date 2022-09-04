package shop.gaship.gashipfront.cart.dummy;

import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipfront.cart.dto.request.CartProductDeleteRequestDto;
import shop.gaship.gashipfront.cart.dto.request.CartProductModifyRequestDto;
import shop.gaship.gashipfront.product.dto.response.ProductAllInfoResponseDto;

import java.time.LocalDateTime;
import java.util.List;

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

    public static ProductAllInfoResponseDto productAllInfoResponseDto() {
        ProductAllInfoResponseDto productAllInfoResponseDto = new ProductAllInfoResponseDto();
        ReflectionTestUtils.setField(productAllInfoResponseDto, "productNo", 1);
        ReflectionTestUtils.setField(productAllInfoResponseDto, "productName", "1");
        ReflectionTestUtils.setField(productAllInfoResponseDto, "productCode", "1");
        ReflectionTestUtils.setField(productAllInfoResponseDto, "categoryName", "1");
        ReflectionTestUtils.setField(productAllInfoResponseDto, "amount", 1L);
        ReflectionTestUtils.setField(productAllInfoResponseDto, "dateTime", LocalDateTime.now());
        ReflectionTestUtils.setField(productAllInfoResponseDto, "manufacturer", "1");
        ReflectionTestUtils.setField(productAllInfoResponseDto, "seller", "1");
        ReflectionTestUtils.setField(productAllInfoResponseDto, "importer", "1");
        ReflectionTestUtils.setField(productAllInfoResponseDto, "installationCost", 1L);
        ReflectionTestUtils.setField(productAllInfoResponseDto, "quality", "1");
        ReflectionTestUtils.setField(productAllInfoResponseDto, "color", "1");
        ReflectionTestUtils.setField(productAllInfoResponseDto, "quantity", 1);
        ReflectionTestUtils.setField(productAllInfoResponseDto, "explanation", "1");
        ReflectionTestUtils.setField(productAllInfoResponseDto, "level", 1);
        ReflectionTestUtils.setField(productAllInfoResponseDto, "deliveryType", "1");
        ReflectionTestUtils.setField(productAllInfoResponseDto, "salesStatus", "1");
        ReflectionTestUtils.setField(productAllInfoResponseDto, "upperName", "1");
        ReflectionTestUtils.setField(productAllInfoResponseDto, "tags", List.of());
        ReflectionTestUtils.setField(productAllInfoResponseDto, "filePaths", List.of());

        return productAllInfoResponseDto;
    }
}

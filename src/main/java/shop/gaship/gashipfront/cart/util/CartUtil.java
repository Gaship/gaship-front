package shop.gaship.gashipfront.cart.util;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import shop.gaship.gashipfront.cart.dto.response.ProductResponseDto;
import shop.gaship.gashipfront.product.dto.response.ProductAllInfoResponseDto;



/**
 * 상품 조회 목록을 변환해줍니다.
 *
 * @author 최정우
 * @since 1.0
 */
public class CartUtil {

    /**
     * 상품 정보 반환값에 주문 수량을 추가하는 로직 입니다.
     *
     * @param productList 상품 반환 정보 리스트
     * @param integerMap 주문 수량
     * @return 주문 수량이 포함된 상품 반환정보 리스트
     */
    public static List<ProductResponseDto> productListToCartList(
            List<ProductAllInfoResponseDto> productList, Map<Integer, Integer> integerMap) {
        return productList.stream().map(ele ->
                        ProductResponseDto.builder()
                                .productNo(ele.getProductNo())
                                .productName(ele.getProductName())
                                .amount(ele.getAmount())
                                .installationCost(ele.getInstallationCost())
                                .orderQuantity(integerMap.get(ele.getProductNo()))
                                .quantity(ele.getQuantity())
                                .filePaths(ele.getFilePaths())
                                .build())
                .collect(Collectors.toList());
    }
}

package shop.gaship.gashipfront.cart.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import shop.gaship.gashipfront.cart.dto.response.ProductResponseDto;
import shop.gaship.gashipfront.product.dto.response.ProductAllInfoResponseDto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 상품 조회 목록을 변환해줍니다.
 *
 * @author 최정우
 * @since 1.0
 */
@Component
public class CartUtil {
    @Value("${gaship-server.gateway-url}")
    private static String gatewayUrl;

    /**
     * 유틸 클래스 추가 생성 불가처리.
     */
    private CartUtil() {
    }

    /**
     * 상품 정보 반환값에 주문 수량을 추가하는 로직 입니다.
     *
     * @param productList 상품 반환 정보 리스트
     * @param integerMap  주문 수량
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
                                .filePaths("http://localhost:7070/api/files/" + ele.getFileNos().get(0) + "/download")
                                .build())
                .collect(Collectors.toList());
    }
}

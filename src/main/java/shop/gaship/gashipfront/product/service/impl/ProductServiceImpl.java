package shop.gaship.gashipfront.product.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.product.adapter.ProductAdapter;
import shop.gaship.gashipfront.product.dto.response.ProductAllInfoResponseDto;
import shop.gaship.gashipfront.product.service.ProductService;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * 설명작성란
 *
 * @author 김민수
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductAdapter productAdapter;

    public PageResponse<ProductAllInfoResponseDto> productAllInfoByPageable(
        String page, String size, String category, String minAmount, String maxAmount) {
        return productAdapter.productListAll(page, size, category, minAmount, maxAmount);
    }
}

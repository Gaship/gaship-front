package shop.gaship.gashipfront.product.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shop.gaship.gashipfront.product.adapter.ProductAdapter;
import shop.gaship.gashipfront.product.dto.request.ProductCreateRequestDto;
import shop.gaship.gashipfront.product.dto.response.ProductAllInfoResponseDto;
import shop.gaship.gashipfront.product.service.ProductService;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * 제품의 데이터 요청 처리를 수행하는 클래스입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductAdapter productAdapter;

    @Override
    public PageResponse<ProductAllInfoResponseDto> productAllInfoByPageable(
        String page, String size, String category, String minAmount, String maxAmount) {
        return productAdapter.productListAll(page, size, category, minAmount, maxAmount);
    }

    @Override
    public PageResponse<ProductAllInfoResponseDto> productCategoryByPageable(Pageable page,
                                                                             String category) {
        return productAdapter.productCategoryList(Integer.parseInt(category), page);
    }

    @Override
    public List<ProductAllInfoResponseDto> findProductNosList(List<Integer> productNos) {
        return productAdapter.productNosList(productNos);
    }

    @Override
    public ProductAllInfoResponseDto findProduct(Integer productNo) {
        return productAdapter.productDetails(productNo);
    }

    @Override
    public void addProduct(List<MultipartFile> multipartFiles,
                           ProductCreateRequestDto createRequest) {
        productAdapter.productAdd(multipartFiles, createRequest);
    }
}

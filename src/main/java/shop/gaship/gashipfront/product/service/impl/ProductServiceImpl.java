package shop.gaship.gashipfront.product.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shop.gaship.gashipfront.config.LocalCacheConfig;
import shop.gaship.gashipfront.product.adapter.ProductAdapter;
import shop.gaship.gashipfront.product.dto.request.ProductCreateRequestDto;
import shop.gaship.gashipfront.product.dto.request.ProductModifyRequestDto;
import shop.gaship.gashipfront.product.dto.request.SalesStatusModifyRequestDto;
import shop.gaship.gashipfront.product.dto.response.ProductAllInfoResponseDto;
import shop.gaship.gashipfront.product.dto.response.ProductByCategoryResponseDto;
import shop.gaship.gashipfront.product.exception.MainProductParseFailureException;
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
    private final ObjectMapper objectMapper;

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
    public PageResponse<ProductAllInfoResponseDto> findProductListByCode(Pageable pageable,
                                                                         String code) {
        return productAdapter.productListSearchCode(code, pageable);
    }

    @Override
    public List<ProductAllInfoResponseDto> findProductNosList(List<Integer> productNos) {
        return productAdapter.productNosList(productNos);
    }

    @Override
    public PageResponse<ProductByCategoryResponseDto> findProductByCategory(Pageable pageable,
                                                                            Integer categoryNo,
                                                                            Long minPrice,
                                                                            Long maxPrice,
                                                                            Boolean isUpper){
        return productAdapter.productByCategoryAndAmount(pageable, categoryNo, minPrice, maxPrice, isUpper);
    }

    @Override
    public ProductAllInfoResponseDto findProduct(Integer productNo) {
        return productAdapter.productDetails(productNo);
    }

    @Override
    @CacheEvict(value = LocalCacheConfig.PRODUCT_CACHE, cacheManager = "redisCacheManager", allEntries = true)
    public void addProduct(List<MultipartFile> multipartFiles,
                           ProductCreateRequestDto createRequest) {
        productAdapter.productAdd(multipartFiles, createRequest);
    }

    @Override
    @CacheEvict(value = LocalCacheConfig.PRODUCT_CACHE, cacheManager = "redisCacheManager", allEntries = true)
    public void modifyProduct(List<MultipartFile> multipartFiles,
                              ProductModifyRequestDto modifyRequest) {
        productAdapter.productModify(multipartFiles, modifyRequest);
    }

    @Override
    @CacheEvict(value = LocalCacheConfig.PRODUCT_CACHE, cacheManager = "redisCacheManager", allEntries = true)
    public void modifySalesStatus(SalesStatusModifyRequestDto salesStatusModifyRequest) {
        productAdapter.salesStatusModify(salesStatusModifyRequest);
    }

    @Override
    @Cacheable(value = LocalCacheConfig.PRODUCT_CACHE, cacheManager = "redisCacheManager")
    public String findMainProducts(String page, String size, String category, String minAmount, String maxAmount) {
        try {
            return objectMapper.writeValueAsString(
                    productAdapter.productListAll(page, size, category, minAmount, maxAmount));
        } catch (JsonProcessingException e) {
            throw new MainProductParseFailureException();
        }
    }
}

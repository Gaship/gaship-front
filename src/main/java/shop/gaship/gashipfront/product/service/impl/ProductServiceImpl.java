package shop.gaship.gashipfront.product.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shop.gaship.gashipfront.product.adapter.ProductAdapter;
import shop.gaship.gashipfront.product.dto.request.ProductCreateRequestDto;
import shop.gaship.gashipfront.product.dto.request.ProductModifyRequestDto;
import shop.gaship.gashipfront.product.dto.request.SalesStatusModifyRequestDto;
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
    private final RedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;
    private static final String PRODUCT_KEY = "common_products";

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
    public ProductAllInfoResponseDto findProduct(Integer productNo) {
        return productAdapter.productDetails(productNo);
    }

    @Override
    public void addProduct(List<MultipartFile> multipartFiles,
                           ProductCreateRequestDto createRequest) {
        productAdapter.productAdd(multipartFiles, createRequest);
    }

    @Override
    public void modifyProduct(List<MultipartFile> multipartFiles,
                              ProductModifyRequestDto modifyRequest) {
        productAdapter.productModify(multipartFiles, modifyRequest);
    }

    @Override
    public void modifySalesStatus(SalesStatusModifyRequestDto salesStatusModifyRequest) {
        productAdapter.salesStatusModify(salesStatusModifyRequest);
    }

    @Override
    public PageResponse<ProductAllInfoResponseDto> findMainProducts(String page, String size, String category, String minAmount, String maxAmount) {
        PageResponse<ProductAllInfoResponseDto> result = null;
        String products = (String) redisTemplate.opsForValue().get(PRODUCT_KEY);

        if (Objects.isNull(products)) {
            result = productAllInfoByPageable(page, size, category, minAmount, maxAmount);
            try {
                redisTemplate.opsForValue().set(PRODUCT_KEY, objectMapper.writeValueAsString(result));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            redisTemplate.expire(PRODUCT_KEY, 60, TimeUnit.SECONDS);
        } else {
            try {
                result = objectMapper.readValue(products, PageResponse.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}

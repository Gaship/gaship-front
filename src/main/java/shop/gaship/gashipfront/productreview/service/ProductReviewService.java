package shop.gaship.gashipfront.productreview.service;

import org.springframework.web.multipart.MultipartFile;
import shop.gaship.gashipfront.productreview.dto.request.ProductReviewRequestDto;
import shop.gaship.gashipfront.productreview.dto.response.ProductReviewResponseDto;
import shop.gaship.gashipfront.response.PageResponse;

/**
 * 상품평 서비스입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
public interface ProductReviewService {
    void addReview(MultipartFile multipartFile, ProductReviewRequestDto createRequest);
    void modifyReview(MultipartFile multipartFile, ProductReviewRequestDto modifyRequest);
    void removeReview(Integer orderProductNo);
    ProductReviewResponseDto findReview(Integer orderProductNo);
    PageResponse<ProductReviewResponseDto> findReviewsByProduct(Integer productNo);
    PageResponse<ProductReviewResponseDto> findReviewsByMember(Integer memberNo);
}

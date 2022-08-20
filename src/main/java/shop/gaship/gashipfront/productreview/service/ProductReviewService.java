package shop.gaship.gashipfront.productreview.service;

import shop.gaship.gashipfront.productreview.dto.response.ProductReviewResponseDto;
import shop.gaship.gashipfront.response.PageResponse;

/**
 * 상품평 서비스입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
public interface ProductReviewService {
    PageResponse<ProductReviewResponseDto> findReviewsByProduct(Integer productNo);
    PageResponse<ProductReviewResponseDto> findReviewsByMember(Integer memberNo);
}

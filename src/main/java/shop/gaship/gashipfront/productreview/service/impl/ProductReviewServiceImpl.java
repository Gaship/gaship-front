package shop.gaship.gashipfront.productreview.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.productreview.adapter.ProductReviewAdapter;
import shop.gaship.gashipfront.productreview.dto.response.ProductReviewResponseDto;
import shop.gaship.gashipfront.productreview.service.ProductReviewService;
import shop.gaship.gashipfront.response.PageResponse;

/**
 * 상품평 서비스 구현체 입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class ProductReviewServiceImpl implements ProductReviewService {
    private final ProductReviewAdapter productReviewAdapter;

    @Override
    public PageResponse<ProductReviewResponseDto> findMemberProductReviews(Integer memberNo) {
        return productReviewAdapter.productReviewListByMember(memberNo);
    }
}

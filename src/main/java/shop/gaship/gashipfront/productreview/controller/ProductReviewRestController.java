package shop.gaship.gashipfront.productreview.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipfront.productreview.dto.response.ProductReviewResponseDto;
import shop.gaship.gashipfront.productreview.service.ProductReviewService;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * 상품평 Rest 컨트롤러입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
public class ProductReviewRestController {
    private final ProductReviewService productReviewService;


    @GetMapping("/api/products/{productNo}/reviews")
    public PageResponse<ProductReviewResponseDto> getProductReviews(@PathVariable Integer productNo,
                                                                    Pageable pageable) {
        return productReviewService.findReviewsByProduct(productNo, pageable);
    }
}

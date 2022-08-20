package shop.gaship.gashipfront.productreview.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import shop.gaship.gashipfront.productreview.service.ProductReviewService;

/**
 * 상품평 컨트롤러입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
public class ProductReviewController {
    private final ProductReviewService productReviewService;

    @GetMapping("/products/{productNo}/reviews")
    public String getProductReviews(@PathVariable Integer productNo,
                                   Model model) {
        model.addAttribute("reviews",
                productReviewService.findReviewsByProduct(productNo));

        return "review/reviewList";
    }

    @GetMapping("/members/{memberNo}/reviews")
    public String getMemberReviews(@PathVariable Integer memberNo,
                                   Model model) {
        model.addAttribute("reviews",
                productReviewService.findReviewsByMember(memberNo));

        return "review/reviewList";
    }
}

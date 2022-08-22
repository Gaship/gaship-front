package shop.gaship.gashipfront.productreview.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import shop.gaship.gashipfront.productreview.dto.request.ProductReviewRequestDto;
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

    @GetMapping("/reviews/{orderProductNo}/add")
    public String getReviewAddForm(@PathVariable Integer orderProductNo,
                                   Model model) {
        model.addAttribute("orderProductNo", orderProductNo);
        return "review/reviewAddForm";
    }

    @GetMapping("/reviews/{orderProductNo}/modify")
    public String getReviewModifyForm(@PathVariable Integer orderProductNo,
                                      Model model) {
        model.addAttribute("review", productReviewService.findReview(orderProductNo));
        return "review/reviewModifyForm";
    }

    @PostMapping("/reviews/{orderProductNo}/add")
    public String addProductReview(@ModelAttribute ProductReviewRequestDto createRequest,
                                   MultipartFile multipartFile) {
        productReviewService.addReview(multipartFile, createRequest);
        return "redirect:/";
    }

    @PostMapping("/reviews/{orderProductNo}/modify")
    public String modifyProductReview(@ModelAttribute ProductReviewRequestDto modifyRequest,
                                      MultipartFile multipartFile) {
        productReviewService.modifyReview(multipartFile, modifyRequest);
        return "redirect:/";
    }

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

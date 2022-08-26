package shop.gaship.gashipfront.productreview.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
                                   HttpServletRequest request,
                                   Model model) {
        request.getSession().setAttribute("redirectUri", request.getHeader("Referer"));
        model.addAttribute("orderProductNo", orderProductNo);
        return "review/reviewAddForm";
    }

    @GetMapping("/reviews/{orderProductNo}/modify")
    public String getReviewModifyForm(@PathVariable Integer orderProductNo,
                                      HttpServletRequest request,
                                      Model model) {
        request.getSession().setAttribute("redirectUri", request.getHeader("Referer"));
        model.addAttribute("review", productReviewService.findReview(orderProductNo));
        return "review/reviewModifyForm";
    }

    @GetMapping("/reviews/{orderProductNo}/remove")
    public String getReviewRemove(@PathVariable Integer orderProductNo,
                                  HttpServletRequest request,
                                  RedirectAttributes redirectAttributes) {
        productReviewService.removeReview(orderProductNo);
        redirectAttributes.addFlashAttribute("message", "상품평이 삭제되었습니다.");
        return "redirect:" + request.getHeader("Referer");
    }

    @PostMapping("/reviews/{orderProductNo}/add")
    public String addProductReview(@ModelAttribute ProductReviewRequestDto createRequest,
                                   MultipartFile multipartFile,
                                   HttpSession session,
                                   RedirectAttributes redirectAttributes) {
        productReviewService.addReview(multipartFile, createRequest);
        redirectAttributes.addFlashAttribute("message", "상품평이 등록되었습니다.");
        return "redirect:" + session.getAttribute("redirectUri");
    }

    @PostMapping("/reviews/{orderProductNo}/modify")
    public String modifyProductReview(@ModelAttribute ProductReviewRequestDto modifyRequest,
                                      MultipartFile multipartFile,
                                      HttpSession session,
                                      RedirectAttributes redirectAttributes) {
        productReviewService.modifyReview(multipartFile, modifyRequest);
        redirectAttributes.addFlashAttribute("message", "상품평이 수정되었습니다.");
        return "redirect:" + session.getAttribute("redirectUri");
    }

    @GetMapping("/admin/reviews")
    public String getReviews(@PageableDefault(size = 5) Pageable pageable,
                             Model model){
        model.addAttribute("reviews",
                productReviewService.findReviews(pageable));
        return "review/adminReviewList";
    }

    @GetMapping("/products/{productNo}/reviews")
    public String getProductReviews(@PathVariable Integer productNo,
                                    @PageableDefault(size = 5) Pageable pageable,
                                    Model model) {
        model.addAttribute("reviews",
                productReviewService.findReviewsByProduct(productNo, pageable));

        return "review/reviewList";
    }

    @GetMapping("/members/{memberNo}/reviews")
    public String getMemberReviews(@PathVariable Integer memberNo,
                                   @PageableDefault(size = 5) Pageable pageable,
                                   Model model) {
        model.addAttribute("reviews",
                productReviewService.findReviewsByMember(memberNo, pageable));

        return "review/reviewList";
    }
}

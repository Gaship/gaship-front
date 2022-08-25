package shop.gaship.gashipfront.productreview.controller;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import shop.gaship.gashipfront.productreview.dto.request.ProductReviewRequestDto;
import shop.gaship.gashipfront.productreview.service.ProductReviewService;
import shop.gaship.gashipfront.security.common.dto.UserDetailsDto;

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
                                  HttpServletRequest request) {
        productReviewService.removeReview(orderProductNo);
        return "redirect:" + request.getHeader("Referer");
    }

    @PostMapping("/reviews/{orderProductNo}/add")
    public String addProductReview(@ModelAttribute ProductReviewRequestDto createRequest,
                                   HttpSession session,
                                   MultipartFile multipartFile) {
        productReviewService.addReview(multipartFile, createRequest);
        return "redirect:" + session.getAttribute("redirectUri");
    }

    @PostMapping("/reviews/{orderProductNo}/modify")
    public String modifyProductReview(@ModelAttribute ProductReviewRequestDto modifyRequest,
                                      HttpSession session,
                                      MultipartFile multipartFile) {
        productReviewService.modifyReview(multipartFile, modifyRequest);
        return "redirect:" + session.getAttribute("redirectUri");
    }

    @GetMapping("/reviews")
    public String getReviews(@PageableDefault(size = 5) Pageable pageable,
                             Model model){
        model.addAttribute("reviews",
                productReviewService.findReviews(pageable));

        return "review/reviewList";
    }

    @GetMapping("/products/{productNo}/reviews")
    public String getProductReviews(@PathVariable Integer productNo,
                                    @PageableDefault(size = 5) Pageable pageable,
                                    Model model,
                                    @AuthenticationPrincipal UserDetailsDto userDetailsDto) {
        if (Objects.nonNull(userDetailsDto)) {
            model.addAttribute("memberNickname",
                    userDetailsDto.getMember().getNickName());
        }
        model.addAttribute("reviews",
                productReviewService.findReviewsByProduct(productNo, pageable));

        return "review/reviewList";
    }

    @GetMapping("/members/{memberNo}/reviews")
    public String getMemberReviews(@PathVariable Integer memberNo,
                                   @PageableDefault(size = 5) Pageable pageable,
                                   Model model,
                                   @AuthenticationPrincipal UserDetailsDto userDetailsDto) {
        if (Objects.nonNull(userDetailsDto)) {
            model.addAttribute("memberNickname",
                    userDetailsDto.getMember().getNickName());
        }
        model.addAttribute("reviews",
                productReviewService.findReviewsByMember(memberNo, pageable));

        return "review/reviewList";
    }
}

package shop.gaship.gashipfront.inquiry.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import shop.gaship.gashipfront.inquiry.dto.response.InquiryListResponseDto;
import shop.gaship.gashipfront.inquiry.service.product.ProductInquiryService;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * 상품문의에 대한 요청을 처리하는 클래스입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
public class ProductInquiryController {
    private final ProductInquiryService inquiryService;

    /**
     * 상품문의 목록을 조회하는 요청을 처리하는 기능입니다.
     *
     * @param pageable 페이지네이션에 맞게 조회하기 위한 정보를 담고있는 객체입니다.
     * @return 200 status code와 함께 PageResponse에 목록들을 body로 담아서 ResponseEntity를 반환합니다.
     * @author 최겸준
     */
    @GetMapping(value = "/product-inquiries")
    public ResponseEntity<PageResponse<InquiryListResponseDto>> productInquiryList(
        Pageable pageable, Model model) {

        PageResponse<InquiryListResponseDto> pageResponse =
            inquiryService.findProductInquiries(pageable);
        model.addAttribute("pageResponse", pageResponse);
        return ResponseEntity.ok(pageResponse);
    }
}

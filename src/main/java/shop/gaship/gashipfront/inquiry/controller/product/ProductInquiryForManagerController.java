package shop.gaship.gashipfront.inquiry.controller.product;

import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryAttribute.KEY_PAGE_RESPONSE;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryViewName.VIEW_NAME_PRODUCT_INQUIRY_LIST;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryViewName.VIEW_NAME_PRODUCT_INQUIRY_LIST_ADMIN;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/admin/inquiries")
public class ProductInquiryForManagerController {

    private final ProductInquiryService productInquiryService;

    /**
     * 상품문의 목록을 조회하는 요청을 처리하는 기능입니다.
     *
     * @param pageable 페이지네이션에 맞게 조회하기 위한 정보를 담고있는 객체입니다.
     * @param model    view에서 처리되어야할 데이터를 저장하는 객체입니다.
     * @return 문의 목록을 보여주는 view name을 반환합니다.
     * @author 최겸준
     */
    @GetMapping(value = "/product-inquiries")
//    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN')")
    public String productInquiryList(
          Pageable pageable, Model model) {

        PageResponse<InquiryListResponseDto> pageResponse =
            productInquiryService.findProductInquiries(pageable);

        model.addAttribute("isUser", false);
        model.addAttribute(KEY_PAGE_RESPONSE.getValue(), pageResponse);
        return VIEW_NAME_PRODUCT_INQUIRY_LIST_ADMIN.getValue();
    }

    /**
     * 답변대기 상태인 상품문의목록 조회요청을 처리하는 기능입니다.
     *
     * @param pageable 페이지네이션에 맞게 조회하기 위한 정보를 담고있는 객체입니다.
     * @param model    view에서 처리되어야할 데이터를 저장하는 객체입니다.
     * @return 문의 목록을 보여주는 view name을 반환합니다.
     * @author 최겸준
     */
    @GetMapping(value = "/product-inquiries/status-hold")
//    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN')")
    public String productInquiryStatusHoldList(
        @PageableDefault Pageable pageable, Model model) {

        PageResponse<InquiryListResponseDto> pageResponse
            = productInquiryService.findProductInquiriesStatusHold(pageable);

        model.addAttribute("isUser", false);
        model.addAttribute(KEY_PAGE_RESPONSE.getValue(), pageResponse);
        return VIEW_NAME_PRODUCT_INQUIRY_LIST.getValue();
    }

    /**
     * 답변완료 상태인 상품문의목록 조회요청을 처리하는 기능입니다.
     *
     * @param pageable 페이지네이션에 맞게 조회하기 위한 정보를 담고있는 객체입니다.
     * @param model    view에서 처리되어야할 데이터를 저장하는 객체입니다.
     * @return 문의 목록을 보여주는 view name을 반환합니다.
     * @author 최겸준
     */
    @GetMapping(value = "/product-inquiries/status-complete")
//    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN')")
    public String productInquiryStatusCompleteList(
        @PageableDefault  Pageable pageable, Model model) {

        PageResponse<InquiryListResponseDto> pageResponse =
            productInquiryService.findProductInquiriesStatusComplete(pageable);

        model.addAttribute("isUser", false);
        model.addAttribute(KEY_PAGE_RESPONSE.getValue(), pageResponse);
        return VIEW_NAME_PRODUCT_INQUIRY_LIST.getValue();
    }
}

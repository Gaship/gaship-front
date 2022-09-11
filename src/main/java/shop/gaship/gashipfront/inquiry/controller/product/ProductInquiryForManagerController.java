package shop.gaship.gashipfront.inquiry.controller.product;

import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryAttribute.KEY_DETAILS;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryAttribute.KEY_PAGE_RESPONSE;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryViewName.VIEW_NAME_PRODUCT_INQUIRY_DETAILS_ADMIN;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryViewName.VIEW_NAME_PRODUCT_INQUIRY_LIST_ADMIN;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.gaship.gashipfront.inquiry.dto.response.InquiryDetailsResponseDto;
import shop.gaship.gashipfront.inquiry.dto.response.InquiryListResponseDto;
import shop.gaship.gashipfront.inquiry.service.common.CommonInquiryService;
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
    private final CommonInquiryService commonInquiryService;

    /**
     * 직원이 상품문의 상세조회 요청을 처리하는 기능입니다.
     *
     * @param inquiryNo 조회의 기준이 되는 문의번호입니다.
     * @return view 경로를 반환합니다.
     * @author 최겸준
     */
    @GetMapping(value = "/product-inquiries/{inquiryNo}/manager")
    public String productInquiryDetails(
        @PathVariable Integer inquiryNo, Model model) {

        InquiryDetailsResponseDto inquiryDetailsResponseDto =
            commonInquiryService.findInquiry(inquiryNo);

        model.addAttribute(KEY_DETAILS.getValue(), inquiryDetailsResponseDto);
        return VIEW_NAME_PRODUCT_INQUIRY_DETAILS_ADMIN.getValue();
    }

    /**
     * 상품문의 목록을 조회하는 요청을 처리하는 기능입니다.
     *
     * @param pageable 페이지네이션에 맞게 조회하기 위한 정보를 담고있는 객체입니다.
     * @param model    view에서 처리되어야할 데이터를 저장하는 객체입니다.
     * @return 문의 목록을 보여주는 view name을 반환합니다.
     * @author 최겸준
     */
    @GetMapping(value = "/product-inquiries")
    public String productInquiryList(
        @PageableDefault Pageable pageable, Model model) {

        PageResponse<InquiryListResponseDto> pageResponse =
            productInquiryService.findProductInquiries(pageable);

        model.addAttribute("filter", "latest");
        model.addAttribute("next", pageResponse.isNext());
        model.addAttribute("previous", pageResponse.isPrevious());
        model.addAttribute("totalPage", pageResponse.getTotalPages());
        model.addAttribute("pageNum", pageResponse.getNumber() + 1);
        model.addAttribute("previousPageNo", pageResponse.getNumber() - 1);
        model.addAttribute("nextPageNo", pageResponse.getNumber() + 1);
        model.addAttribute("uri", "/admin/inquiries/product-inquiries");
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
    public String productInquiryStatusHoldList(
        @PageableDefault Pageable pageable, Model model) {

        PageResponse<InquiryListResponseDto> pageResponse
            = productInquiryService.findProductInquiriesStatusHold(pageable);

        model.addAttribute("next", pageResponse.isNext());
        model.addAttribute("previous", pageResponse.isPrevious());
        model.addAttribute("totalPage", pageResponse.getTotalPages());
        model.addAttribute("pageNum", pageResponse.getNumber() + 1);
        model.addAttribute("previousPageNo", pageResponse.getNumber() - 1);
        model.addAttribute("nextPageNo", pageResponse.getNumber() + 1);
        model.addAttribute("uri", "/admin/inquiries/product-inquiries/status-hold");
        model.addAttribute("filter", "hold");
        model.addAttribute(KEY_PAGE_RESPONSE.getValue(), pageResponse);
        return VIEW_NAME_PRODUCT_INQUIRY_LIST_ADMIN.getValue();
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
    public String productInquiryStatusCompleteList(
        @PageableDefault  Pageable pageable, Model model) {

        PageResponse<InquiryListResponseDto> pageResponse =
            productInquiryService.findProductInquiriesStatusComplete(pageable);

        model.addAttribute("next", pageResponse.isNext());
        model.addAttribute("previous", pageResponse.isPrevious());
        model.addAttribute("totalPage", pageResponse.getTotalPages());
        model.addAttribute("pageNum", pageResponse.getNumber() + 1);
        model.addAttribute("previousPageNo", pageResponse.getNumber() - 1);
        model.addAttribute("nextPageNo", pageResponse.getNumber() + 1);
        model.addAttribute("uri", "/admin/inquiries/product-inquiries/status-complete");
        model.addAttribute("filter", "complete");
        model.addAttribute(KEY_PAGE_RESPONSE.getValue(), pageResponse);
        return VIEW_NAME_PRODUCT_INQUIRY_LIST_ADMIN.getValue();
    }
}

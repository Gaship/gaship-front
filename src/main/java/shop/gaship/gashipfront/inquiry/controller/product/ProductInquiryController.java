package shop.gaship.gashipfront.inquiry.controller.product;

import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryAttribute.KEY_INQUIRY_DETAILS;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryAttribute.KEY_PAGE_RESPONSE;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryViewName.VIEW_NAME_PRODUCT_INQUIRY_DETAILS;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryViewName.VIEW_NAME_PRODUCT_INQUIRY_LIST;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("/inquires")
public class ProductInquiryController {

    private final ProductInquiryService productInquiryService;
    private final CommonInquiryService commonInquiryService;

    /**
     * 관리자 또는 회원이 상품상세페이지에서 해당하는 상품에 대한 상품문의목록 조회요청을 처리하는 기능입니다.
     *
     * @param pageable  페이지네이션에 맞게 조회하기 위한 정보를 담고있는 객체입니다.
     * @param productNo 기준이 되는 상품의 식별번호입니다.
     * @param model view에서 처리되어야할 데이터를 저장하는 객체입니다.
     * @return 문의 목록을 보여주는 view name을 반환합니다.
     * @author 최겸준
     */
    @GetMapping(value = "/product/{productNo}")
    public String productInquiryProductList(
        Pageable pageable, @PathVariable Integer productNo,
        Model model) {

        PageResponse<InquiryListResponseDto> pageResponse =
            productInquiryService.findProductInquiriesByProductNo(pageable, productNo);
        model.addAttribute(KEY_PAGE_RESPONSE.getValue(), pageResponse);
        return VIEW_NAME_PRODUCT_INQUIRY_LIST.getValue();
    }

    /**
     * 관리자 또는 회원본인에 대한 상품문의 상세조회 요청을 처리하는 기능입니다.
     *
     * @param inquiryNo 조회의 기준이 되는 문의번호입니다.
     * @return view 경로를 반환합니다.
     * @author 최겸준
     */
    @GetMapping(value = "/product-inquiries/{inquiryNo}")
    public String productInquiryDetails(
        @PathVariable Integer inquiryNo, Model model) {
        InquiryDetailsResponseDto inquiryDetailsResponseDto =
            commonInquiryService.findInquiry(inquiryNo);

        model.addAttribute(KEY_INQUIRY_DETAILS.getValue(), inquiryDetailsResponseDto);
        return VIEW_NAME_PRODUCT_INQUIRY_DETAILS.getValue();
    }
}

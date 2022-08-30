package shop.gaship.gashipfront.inquiry.controller.product;

import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryAttribute.KEY_DETAILS;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryAttribute.KEY_PAGE_RESPONSE;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryViewName.VIEW_NAME_PRODUCT_INQUIRY_ADD_FORM;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryViewName.VIEW_NAME_PRODUCT_INQUIRY_DETAILS;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryViewName.VIEW_NAME_PRODUCT_INQUIRY_LIST;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.gaship.gashipfront.inquiry.dto.request.view.ProductInfo;
import shop.gaship.gashipfront.inquiry.dto.response.InquiryDetailsResponseDto;
import shop.gaship.gashipfront.inquiry.dto.response.InquiryListResponseDto;
import shop.gaship.gashipfront.inquiry.service.common.CommonInquiryService;
import shop.gaship.gashipfront.inquiry.service.product.ProductInquiryService;
import shop.gaship.gashipfront.inquiry.util.RoleUserMySelfProcessor;
import shop.gaship.gashipfront.security.common.dto.UserDetailsDto;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * 상품문의에 대한 요청을 처리하는 클래스입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/inquiries")
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
        Model model, @AuthenticationPrincipal UserDetailsDto userDetailsDto) {

        PageResponse<InquiryListResponseDto> pageResponse =
            productInquiryService.findProductInquiriesByProductNo(pageable, productNo);

        Boolean isUser = RoleUserMySelfProcessor.setSelfList(userDetailsDto, pageResponse.getContent());

        model.addAttribute("isUser", isUser);
        model.addAttribute(KEY_PAGE_RESPONSE.getValue(), pageResponse);
        return VIEW_NAME_PRODUCT_INQUIRY_LIST.getValue();
    }

    /**
     * 상품문의 상세조회 요청을 처리하는 기능입니다.
     *
     * @param inquiryNo 조회의 기준이 되는 문의번호입니다.
     * @return view 경로를 반환합니다.
     * @author 최겸준
     */
    @GetMapping(value = "/product-inquiries/{inquiryNo}")
//    @PreAuthorize("isAuthenticated()")
    public String productInquiryDetails(
        @PathVariable Integer inquiryNo, Model model,
        @AuthenticationPrincipal UserDetailsDto userDetailsDto) {

        InquiryDetailsResponseDto inquiryDetailsResponseDto =
            commonInquiryService.findInquiry(inquiryNo);

        Boolean isUser = RoleUserMySelfProcessor.setSelf(userDetailsDto, inquiryDetailsResponseDto);

        model.addAttribute("isUser", isUser);
        model.addAttribute(KEY_DETAILS.getValue(), inquiryDetailsResponseDto);
        return VIEW_NAME_PRODUCT_INQUIRY_DETAILS.getValue();
    }

    /**
     * 상품문의를 추가하기 위한 추가페이지 조회 요청을 처리합니다.
     *
     * @return 상품문의 추가페이지에 대한 view 경로를 반환합니다.
     * @author 최겸준
     */
    @GetMapping(value = "/show-form/product-inquiry-add", params = {"productNo", "productName"})
    public String productInquiryAddForm(@Valid ProductInfo productInfo, Model model) {

        model.addAttribute("productNo", productInfo.getProductNo());
        model.addAttribute("productName", productInfo.getProductName());
        return VIEW_NAME_PRODUCT_INQUIRY_ADD_FORM.getValue();
    }
}

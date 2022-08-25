package shop.gaship.gashipfront.inquiry.controller.product;

import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryAttribute.KEY_IS_SUCCESS;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryType.PRODUCT_INQUIRY;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryAttribute.KEY_PAGE_RESPONSE;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryAttribute.KEY_SUCCESS_MESSAGE;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryAttribute.VALUE_MESSAGE_PRODUCT_INQUIRY_ADD_SUCCESS;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryViewName.VIEW_NAME_PRODUCT_INQUIRY_ADD_FORM;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryViewName.VIEW_NAME_PRODUCT_INQUIRY_LIST;

import java.util.Objects;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.gaship.gashipfront.exceptions.MemberNoNotFoundException;
import shop.gaship.gashipfront.inquiry.dto.request.InquiryAddRequestDto;
import shop.gaship.gashipfront.inquiry.dto.response.InquiryListResponseDto;
import shop.gaship.gashipfront.inquiry.service.common.CommonInquiryService;
import shop.gaship.gashipfront.inquiry.service.product.ProductInquiryService;
import shop.gaship.gashipfront.inquiry.util.InquirySuccessVerifier;
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
@RequestMapping("/inquires")
public class ProductInquiryForMemberController {

    private final ProductInquiryService productInquiryService;
    private final CommonInquiryService commonInquiryService;

    /**
     * 본인인 회원에 대한 상품문의목록 조회요청을 처리하는 기능입니다.
     *
     * @param pageable       페이지네이션에 맞게 조회하기 위한 정보를 담고있는 객체입니다.
     * @param model          view에서 처리되어야할 데이터를 저장하는 객체입니다.
     * @param userDetailsDto 로그인된 사용자정보를 담고 있는 객체입니다.
     * @return 문의 목록을 보여주는 view name을 반환합니다.
     * @author 최겸준
     */
    @GetMapping(value = "/member-self/product-inquiries")
    public String productInquiryMemberList(
        Pageable pageable, Model model, HttpSession session,
        @AuthenticationPrincipal UserDetailsDto userDetailsDto) {

        InquirySuccessVerifier.verify(model, session);

        Integer memberNo = userDetailsDto.getMemberNo();
        if (Objects.isNull(memberNo)) {
            throw new MemberNoNotFoundException();
        }

        PageResponse<InquiryListResponseDto> pageResponse =
            productInquiryService.findProductInquiriesByMemberNo(pageable, memberNo);
        model.addAttribute(KEY_PAGE_RESPONSE.getValue(), pageResponse);
        return VIEW_NAME_PRODUCT_INQUIRY_LIST.getValue();
    }

    /**
     * 상품문의를 추가하기 위한 추가페이지 조회 요청을 처리합니다.
     *
     * @return 상품문의 추가페이지에 대한 view 경로를 반환합니다.
     * @author 최겸준
     */
    @GetMapping("/show-form/product-inquiry-add")
    public String productInquiryAddForm(Integer productNo, Model model) {

        model.addAttribute("productNo", productNo);
        return VIEW_NAME_PRODUCT_INQUIRY_ADD_FORM.getValue();
    }

    /**
     * 회원이 상품문의를 추가하기 위한 요청을 처리합니다.
     *
     * @param inquiryAddRequestDto 해당 server로 온 요청을 바인딩하기위한 DTO 객체입니다.
     * @return view 경로를 반환합니다.
     * @author 최겸준
     */
    @PostMapping(value = "/inquiries/product-inquiry")
    public String productInquiryAdd(@Valid InquiryAddRequestDto inquiryAddRequestDto,
                                    HttpSession session) {
        inquiryAddRequestDto.setIsProduct(PRODUCT_INQUIRY.getValue());
        commonInquiryService.addInquiry(inquiryAddRequestDto);

        session.setAttribute(KEY_IS_SUCCESS.getValue(), Boolean.TRUE);
        session.setAttribute(KEY_SUCCESS_MESSAGE.getValue(),
            VALUE_MESSAGE_PRODUCT_INQUIRY_ADD_SUCCESS.getValue());

        return "redirect:/inquires/member-self/product-inquiries";
    }
}

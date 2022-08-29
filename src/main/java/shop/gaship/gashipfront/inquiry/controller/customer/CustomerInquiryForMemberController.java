package shop.gaship.gashipfront.inquiry.controller.customer;

import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryAttribute.KEY_PAGE_RESPONSE;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryAttribute.KEY_SUCCESS_MESSAGE;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryAttribute.VALUE_MESSAGE_CUSTOMER_INQUIRY_ADD_SUCCESS;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryType.CUSTOMER_INQUIRY;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryViewName.VIEW_NAME_CUSTOMER_INQUIRY_ADD_FORM;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryViewName.VIEW_NAME_CUSTOMER_INQUIRY_LIST;

import java.util.Objects;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.gaship.gashipfront.exceptions.MemberNotCreationException;
import shop.gaship.gashipfront.inquiry.dto.request.InquiryAddRequestDto;
import shop.gaship.gashipfront.inquiry.dto.response.InquiryListResponseDto;
import shop.gaship.gashipfront.inquiry.service.common.CommonInquiryService;
import shop.gaship.gashipfront.inquiry.service.customer.CustomerInquiryService;
import shop.gaship.gashipfront.security.common.dto.UserDetailsDto;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * 회원권한인 경우 고객문의에 대한 요청을 처리하는 클래스입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
@Controller
@RequestMapping("/inquiries")
@RequiredArgsConstructor
public class CustomerInquiryForMemberController {

    private final CustomerInquiryService customerInquiryService;
    private final CommonInquiryService commonInquiryService;

    /**
     * 본인인 회원에 대한 고객문의목록 조회요청을 처리하는 기능입니다.
     *
     * @param model          view에서 처리되어야할 데이터를 저장하는 객체입니다.
     * @param userDetailsDto 로그인된 사용자정보를 담고 있는 객체입니다.
     * @return 문의 목록을 보여주는 view name을 반환합니다.
     * @author 최겸준
     */
    @GetMapping(value = "/member-self/customer-inquiries")
    public String customerInquiryMemberList(
        Model model,
        @AuthenticationPrincipal UserDetailsDto userDetailsDto) {

        model.addAttribute("memberNo", userDetailsDto.getMemberNo());
        model.addAttribute("whereUri", "customer");
        return VIEW_NAME_CUSTOMER_INQUIRY_LIST.getValue();
    }

    /**
     * 고객문의를 추가하기 위한 추가페이지 조회 요청을 처리합니다.
     *
     * @return 고객문의 추가페이지에 대한 view name을 반환합니다.
     * @author 최겸준
     */
    @GetMapping("/show-form/customer-inquiry-add")
    public String customerInquiryAddForm() {

        return VIEW_NAME_CUSTOMER_INQUIRY_ADD_FORM.getValue();
    }

    /**
     * 본인인 회원이 고객문의를 추가하기 위한 요청을 처리합니다.
     *
     * @param inquiryAddRequestDto 해당 server로 온 요청을 바인딩하기위한 DTO 객체입니다.
     * @return view 경로를 반환합니다.
     * @author 최겸준
     */
    @PostMapping(value = "/customer-inquiry")
    public String customerInquiryAdd(@Valid InquiryAddRequestDto inquiryAddRequestDto,
                                     RedirectAttributes redirectAttributes,
                                     @AuthenticationPrincipal UserDetailsDto userDetailsDto) {

        inquiryAddRequestDto.setIsProduct(CUSTOMER_INQUIRY.getValue());
        inquiryAddRequestDto.setMemberNo(userDetailsDto.getMemberNo());
        commonInquiryService.addInquiry(inquiryAddRequestDto);

        redirectAttributes.addFlashAttribute(KEY_SUCCESS_MESSAGE.getValue(),
            VALUE_MESSAGE_CUSTOMER_INQUIRY_ADD_SUCCESS.getValue());
        return "redirect:/inquiries/member-self/customer-inquiries";
    }
}

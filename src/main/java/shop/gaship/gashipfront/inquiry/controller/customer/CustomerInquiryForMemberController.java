package shop.gaship.gashipfront.inquiry.controller.customer;

import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryAttribute.KEY_DETAILS;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryAttribute.KEY_PAGE_RESPONSE;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryAttribute.KEY_SUCCESS_MESSAGE;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryAttribute.VALUE_MESSAGE_CUSTOMER_INQUIRY_ADD_SUCCESS;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryType.CUSTOMER_INQUIRY;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryViewName.VIEW_NAME_CUSTOMER_INQUIRY_ADD_FORM;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryViewName.VIEW_NAME_CUSTOMER_INQUIRY_DETAILS;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryViewName.VIEW_NAME_CUSTOMER_INQUIRY_LIST;

import java.util.Objects;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.gaship.gashipfront.exceptions.MemberNotCreationException;
import shop.gaship.gashipfront.inquiry.dto.request.InquiryAddRequestDto;
import shop.gaship.gashipfront.inquiry.dto.response.InquiryDetailsResponseDto;
import shop.gaship.gashipfront.inquiry.dto.response.InquiryListResponseDto;
import shop.gaship.gashipfront.inquiry.service.common.CommonInquiryService;
import shop.gaship.gashipfront.inquiry.service.customer.CustomerInquiryService;
import shop.gaship.gashipfront.inquiry.util.RoleUserMySelfProcessor;
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
@Secured("ROLE_USER")
public class CustomerInquiryForMemberController {

    private final CustomerInquiryService customerInquiryService;
    private final CommonInquiryService commonInquiryService;


    /**
     * 회원본인에 대한 고객문의 상세조회 요청을 처리하는 기능입니다.
     *
     * @param inquiryNo 조회의 기준이 되는 문의번호입니다.
     * @return view 경로를 반환합니다.
     * @author 최겸준
     */
    @GetMapping(value = "/customer-inquiries/{inquiryNo}")
    public String customerInquiryDetails(
        @PathVariable Integer inquiryNo, Model model) {

        InquiryDetailsResponseDto inquiryDetailsResponseDto =
            commonInquiryService.findInquiry(inquiryNo);

        model.addAttribute(KEY_DETAILS.getValue(), inquiryDetailsResponseDto);
        return VIEW_NAME_CUSTOMER_INQUIRY_DETAILS.getValue();
    }

    /**
     * 본인인 회원에 대한 고객문의목록 조회요청을 처리하는 기능입니다.
     *
     * @param model          view에서 처리되어야할 데이터를 저장하는 객체입니다.
     * @return 문의 목록을 보여주는 view name을 반환합니다.
     * @author 최겸준
     */
    @GetMapping(value = "/member-self/customer-inquiries")
    public String customerInquiryMemberList(Pageable pageable,
                                                    @AuthenticationPrincipal UserDetailsDto userDetailsDto, Model model) {

        Integer memberNo = userDetailsDto.getMemberNo();
        if (Objects.isNull(memberNo)) {
            throw new MemberNotCreationException();
        }

        PageResponse<InquiryListResponseDto> pageResponse =
            customerInquiryService.findCustomerInquiriesByMemberNo(pageable, memberNo);
        RoleUserMySelfProcessor.setSelfList(userDetailsDto, pageResponse.getContent());

        model.addAttribute("next", pageResponse.isNext());
        model.addAttribute("previous", pageResponse.isPrevious());
        model.addAttribute("totalPage", pageResponse.getTotalPages());
        model.addAttribute("pageNum", pageResponse.getNumber() + 1);
        model.addAttribute("previousPageNo", pageResponse.getNumber() - 1);
        model.addAttribute("nextPageNo", pageResponse.getNumber() + 1);
        model.addAttribute("uri", "/admin/inquiries/product-inquiries");
        model.addAttribute(KEY_PAGE_RESPONSE.getValue(), pageResponse);
        return VIEW_NAME_CUSTOMER_INQUIRY_LIST.getValue();
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

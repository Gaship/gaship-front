package shop.gaship.gashipfront.inquiry.controller.customer;

import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryAttribute.KEY_DETAILS;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryAttribute.KEY_PAGE_RESPONSE;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryViewName.VIEW_NAME_CUSTOMER_INQUIRY_DETAILS;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryViewName.VIEW_NAME_CUSTOMER_INQUIRY_DETAILS_ADMIN;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryViewName.VIEW_NAME_CUSTOMER_INQUIRY_LIST;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryViewName.VIEW_NAME_CUSTOMER_INQUIRY_LIST_ADMIN;

import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.gaship.gashipfront.inquiry.dto.response.InquiryDetailsResponseDto;
import shop.gaship.gashipfront.inquiry.dto.response.InquiryListResponseDto;
import shop.gaship.gashipfront.inquiry.service.common.CommonInquiryService;
import shop.gaship.gashipfront.inquiry.service.customer.CustomerInquiryService;
import shop.gaship.gashipfront.inquiry.util.RoleUserMySelfProcessor;
import shop.gaship.gashipfront.security.common.dto.UserDetailsDto;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * 직원 이상의 권한인경우 고객문의에 대한 요청을 처리하는 클래스입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
@Controller
@RequestMapping("/admin/inquiries")
@RequiredArgsConstructor
public class CustomerInquiryForManagerController {

    private final CustomerInquiryService customerInquiryService;
    private final CommonInquiryService commonInquiryService;

    /**
     * 관리자에 대한 고객문의 상세조회 요청을 처리하는 기능입니다.
     *
     * @param inquiryNo 조회의 기준이 되는 문의번호입니다.
     * @return view 경로를 반환합니다.
     * @author 최겸준
     */
    @GetMapping(value = "/customer-inquiries/{inquiryNo}/manager")
    public String customerInquiryDetails(
        @PathVariable Integer inquiryNo, Model model) {

        InquiryDetailsResponseDto inquiryDetailsResponseDto =
            commonInquiryService.findInquiry(inquiryNo);

        model.addAttribute(KEY_DETAILS.getValue(), inquiryDetailsResponseDto);
        return VIEW_NAME_CUSTOMER_INQUIRY_DETAILS_ADMIN.getValue();
    }


    /**
     * 관리자가 모든 고객문의 목록을 최신순으로 조회하는 요청을 처리하는 기능입니다.
     *
     * @param pageable 페이지네이션에 맞게 조회하기 위한 정보를 담고있는 객체입니다.
     * @param model    view에서 처리되어야할 데이터를 저장하는 객체입니다.
     * @return 문의 목록을 보여주는 view name을 반환합니다.
     * @author 최겸준
     */
    @GetMapping(value = "/customer-inquiries")
    public String customerInquiryList(@PageableDefault Pageable pageable, Model model) {

        PageResponse<InquiryListResponseDto> pageResponse =
            customerInquiryService.findCustomerInquiries(pageable);

        model.addAttribute("next", pageResponse.isNext());
        model.addAttribute("previous", pageResponse.isPrevious());
        model.addAttribute("totalPage", pageResponse.getTotalPages());
        model.addAttribute("pageNum", pageResponse.getNumber() + 1);
        model.addAttribute("previousPageNo", pageResponse.getNumber() - 1);
        model.addAttribute("nextPageNo", pageResponse.getNumber() + 1);
        model.addAttribute("uri", "/admin/inquiries/customer-inquiries");
        model.addAttribute(KEY_PAGE_RESPONSE.getValue(), pageResponse);
        return VIEW_NAME_CUSTOMER_INQUIRY_LIST_ADMIN.getValue();
    }

    /**
     * 답변대기 상태인 고객문의목록 조회요청을 처리하는 기능입니다.
     *
     * @param pageable 페이지네이션에 맞게 조회하기 위한 정보를 담고있는 객체입니다.
     * @param model    view에서 처리되어야할 데이터를 저장하는 객체입니다.
     * @return 문의 목록을 보여주는 view name을 반환합니다.
     * @author 최겸준
     */
    @GetMapping(value = "/customer-inquiries/status-hold")
    public String customerInquiryStatusHoldList(@PageableDefault Pageable pageable, Model model) {

        PageResponse<InquiryListResponseDto> pageResponse =
            customerInquiryService.findCustomerInquiriesStatusHold(pageable);

        model.addAttribute("next", pageResponse.isNext());
        model.addAttribute("previous", pageResponse.isPrevious());
        model.addAttribute("totalPage", pageResponse.getTotalPages());
        model.addAttribute("pageNum", pageResponse.getNumber() + 1);
        model.addAttribute("previousPageNo", pageResponse.getNumber() - 1);
        model.addAttribute("nextPageNo", pageResponse.getNumber() + 1);
        model.addAttribute("uri", "/admin/inquiries/customer-inquiries/status-hold");
        model.addAttribute(KEY_PAGE_RESPONSE.getValue(), pageResponse);
        return VIEW_NAME_CUSTOMER_INQUIRY_LIST_ADMIN.getValue();
    }

    /**
     * 답변완료 상태인 고객문의목록 조회요청을 처리하는 기능입니다.
     *
     * @param pageable 페이지네이션에 맞게 조회하기 위한 정보를 담고있는 객체입니다.
     * @param model    view에서 처리되어야할 데이터를 저장하는 객체입니다.
     * @return 문의 목록을 보여주는 view name을 반환합니다.
     * @author 최겸준
     */
    @GetMapping(value = "/customer-inquiries/status-complete")
    public String customerInquiryStatusCompleteList(@PageableDefault Pageable pageable, Model model) {

        PageResponse<InquiryListResponseDto> pageResponse =
            customerInquiryService.findCustomerInquiriesStatusComplete(pageable);

        model.addAttribute("next", pageResponse.isNext());
        model.addAttribute("previous", pageResponse.isPrevious());
        model.addAttribute("totalPage", pageResponse.getTotalPages());
        model.addAttribute("pageNum", pageResponse.getNumber() + 1);
        model.addAttribute("previousPageNo", pageResponse.getNumber() - 1);
        model.addAttribute("nextPageNo", pageResponse.getNumber() + 1);
        model.addAttribute("uri", "/admin/inquiries/customer-inquiries/status-complete");
        model.addAttribute(KEY_PAGE_RESPONSE.getValue(), pageResponse);
        return VIEW_NAME_CUSTOMER_INQUIRY_LIST_ADMIN.getValue();
    }

}

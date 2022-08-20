package shop.gaship.gashipfront.inquiry.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.gaship.gashipfront.inquiry.dto.response.InquiryListResponseDto;
import shop.gaship.gashipfront.inquiry.service.customer.CustomerInquiryService;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * 고객문의에 대한 요청을 처리하는 클래스입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
@Controller
@RequestMapping("/inquiries")
@RequiredArgsConstructor
public class CustomerInquiryController {

    private final CustomerInquiryService customerInquiryService;

    /**
     * 고객문의 목록을 조회하는 요청을 처리하는 기능입니다.
     *
     * @param pageable 페이지네이션에 맞게 조회하기 위한 정보를 담고있는 객체입니다.
     * @return view name을 반환합니다.
     * @author 최겸준
     */
    @GetMapping(value = "/customer-inquiries")
    public String customerInquiryList(
        @PageableDefault Pageable pageable, Model model) {

        PageResponse<InquiryListResponseDto> pageResponse = customerInquiryService.findCustomerInquiries(pageable);
        model.addAttribute("pageResponse", pageResponse);
        return "";
    }

    /**
     * 답변대기 상태인 고객문의목록 조회요청을 처리하는 기능입니다.
     *
     * @param pageable 페이지네이션에 맞게 조회하기 위한 정보를 담고있는 객체입니다.
     * @return view name을 반환합니다.
     * @author 최겸준
     */
    @GetMapping(value = "/customer-inquiries/status-hold")
    public String customerInquiryStatusHoldList(Pageable pageable, Model model) {

        PageResponse<InquiryListResponseDto> pageResponse = customerInquiryService.findCustomerInquiriesStatusHold(pageable);
        model.addAttribute("pageResponse", pageResponse);
        return "";
    }

    /**
     * 답변완료 상태인 고객문의목록 조회요청을 처리하는 기능입니다.
     *
     * @param pageable 페이지네이션에 맞게 조회하기 위한 정보를 담고있는 객체입니다.
     * @return view name을 반환합니다.
     * @author 최겸준
     */
    @GetMapping(value = "/customer-inquiries/status-complete")
    public String customerInquiryStatusCompleteList(
        Pageable pageable, Model model) {

        PageResponse<InquiryListResponseDto> pageResponse = customerInquiryService.findCustomerInquiriesStatusComplete(pageable);
        model.addAttribute("pageResponse", pageResponse);
        return "";
    }

    /**
     * 특정회원에 대한 고객문의목록 조회요청을 처리하는 기능입니다.
     *
     * @param memberNo 기준이 되는 회원의 식별번호입니다.
     * @param pageable 페이지네이션에 맞게 조회하기 위한 정보를 담고있는 객체입니다.
     * @return view name을 반환합니다.
     * @author 최겸준
     */
    @GetMapping(value = "/member/{memberNo}/customer-inquiries")
    public String customerInquiryMemberList(
        Pageable pageable, @PathVariable Integer memberNo, Model model) {

        PageResponse<InquiryListResponseDto> pageResponse = customerInquiryService.findInquiriesByMemberNo(pageable, memberNo);
        model.addAttribute("pageResponse", pageResponse);
        return "";
    }
}

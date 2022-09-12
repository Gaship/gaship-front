package shop.gaship.gashipfront.inquiry.controller.customer;

import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryAttribute.KEY_DETAILS;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryAttribute.KEY_PAGE_RESPONSE;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryViewName.VIEW_NAME_CUSTOMER_INQUIRY_DETAILS_ADMIN;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryViewName.VIEW_NAME_CUSTOMER_INQUIRY_LIST_ADMIN;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
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
import shop.gaship.gashipfront.inquiry.service.customer.CustomerInquiryService;
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
     * 답변대기 상태인 고객문의목록 조회요청을 처리하는 기능입니다.
     *
     * @param pageable 페이지네이션에 맞게 조회하기 위한 정보를 담고있는 객체입니다.
     * @param model    view에서 처리되어야할 데이터를 저장하는 객체입니다.
     * @return 문의 목록을 보여주는 view name을 반환합니다.
     * @author 최겸준
     */
    @GetMapping(value = "/customer-inquiries/status-hold")
    public String customerInquiryStatusHoldList(@PageableDefault Pageable pageable, Model model) {

        Instant stime = Instant.now();
        PageResponse<InquiryListResponseDto> pageResponse =
            customerInquiryService.findCustomerInquiriesStatusHold(pageable);
        Instant etime = Instant.now();
        long betweenTime = Duration.between(stime, etime).toMillis();
        double resultTime = betweenTime / 1000.0;

        model.addAttribute("filter", "hold");
        model.addAttribute("resultTime", resultTime);
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
    @GetMapping(value = {"/customer-inquiries/status-complete", "/customer-inquiries"})
    public String customerInquiryStatusCompleteList(@PageableDefault Pageable pageable, Integer inquiryNo,
                                                    Integer prevPage, Boolean isPrev, Model model, HttpServletRequest request) {

        if (Objects.isNull(inquiryNo)) {
            inquiryNo = 0;
            isPrev = true;
            prevPage = 0;
        }

        boolean isStatus = request.getRequestURI().contains("status");
        Instant stime = Instant.now();
        PageResponse<InquiryListResponseDto> pageResponse = getPageResponse(pageable, inquiryNo, isPrev, isStatus);
        Instant etime = Instant.now();

        double resultTime = Duration.between(stime, etime).toMillis() / 1000.0;
        Integer nowPage = getNowPage(pageable, prevPage, isPrev, pageResponse);

        setModel(model, pageResponse, resultTime, nowPage, isStatus);
        return VIEW_NAME_CUSTOMER_INQUIRY_LIST_ADMIN.getValue();
    }

    private PageResponse<InquiryListResponseDto> getPageResponse(
        Pageable pageable, Integer inquiryNo, Boolean isPrev, boolean isStatus) {
        PageResponse<InquiryListResponseDto> pageResponse;
        if (inquiryNo.equals(0)) {
            if (isStatus) {
                pageResponse = customerInquiryService.findCustomerInquiriesStatusComplete(pageable);
            } else {
                pageResponse = customerInquiryService.findCustomerInquiries(pageable);
            }

        } else if (isPrev) {
            if (isStatus) {
                pageResponse = customerInquiryService.findCustomerInquiriesStatusCompletePrevPage(
                    pageable, inquiryNo);
            } else {
                pageResponse = customerInquiryService.findCustomerInquiriesAllPrevPage(
                    pageable, inquiryNo);
            }
        } else {
            if (isStatus) {
                pageResponse = customerInquiryService.findCustomerInquiriesStatusCompleteNextPage(
                    pageable, inquiryNo);
            } else {
                pageResponse = customerInquiryService.findCustomerInquiriesAllNextPage(
                    pageable, inquiryNo);
            }
        }

        return pageResponse;
    }


    private Integer getNowPage(Pageable pageable, Integer prevPage, Boolean isPrev,
                               PageResponse<InquiryListResponseDto> pageResponse) {
        Integer nowPage;
        if (isPrev) {
            nowPage = prevPage - (pageable.getPageNumber() + 1);
        } else {
            nowPage = prevPage + (pageable.getPageNumber() + 1);
        }

        if (nowPage < 1) {
            nowPage = 1;
        } else if (nowPage > pageResponse.getTotalPages()) {
            nowPage = pageResponse.getTotalPages();
        }
        return nowPage;
    }

    private void setModel(Model model, PageResponse<InquiryListResponseDto> pageResponse,
                          double resultTime, Integer nowPage, boolean status) {

        List<InquiryListResponseDto> content = pageResponse.getContent();
        int firstNo = content.get(0).getInquiryNo();
        int lastNo = content.get(content.size() - 1).getInquiryNo();

        model.addAttribute("nowPage", nowPage);
        model.addAttribute("firstNo", firstNo);
        model.addAttribute("lastNo", lastNo);
        model.addAttribute("resultTime", resultTime);
        model.addAttribute(KEY_PAGE_RESPONSE.getValue(), pageResponse);

        if (status) {
            model.addAttribute("filter", "complete");
            model.addAttribute("uri", "/admin/inquiries/customer-inquiries/status-complete");
            return;
        }
        model.addAttribute("filter", "latest");
        model.addAttribute("uri", "/admin/inquiries/customer-inquiries");
    }
}

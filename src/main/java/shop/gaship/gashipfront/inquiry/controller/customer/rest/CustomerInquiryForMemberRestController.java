package shop.gaship.gashipfront.inquiry.controller.customer.rest;

import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryAttribute.KEY_PAGE_RESPONSE;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryViewName.VIEW_NAME_CUSTOMER_INQUIRY_LIST;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipfront.exceptions.MemberNotCreationException;
import shop.gaship.gashipfront.inquiry.dto.response.InquiryListResponseDto;
import shop.gaship.gashipfront.inquiry.service.common.CommonInquiryService;
import shop.gaship.gashipfront.inquiry.service.customer.CustomerInquiryService;
import shop.gaship.gashipfront.security.common.dto.UserDetailsDto;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * @author : 최겸준
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/js/inquiries")
public class CustomerInquiryForMemberRestController {

    private final CustomerInquiryService customerInquiryService;
    private final CommonInquiryService commonInquiryService;

    @GetMapping(value = "/member-self/customer-inquiries")
    public PageResponse<InquiryListResponseDto> customerInquiryMemberList(
        Pageable pageable,
        @AuthenticationPrincipal UserDetailsDto userDetailsDto) {

        Integer memberNo = userDetailsDto.getMemberNo();
        if (Objects.isNull(memberNo)) {
            throw new MemberNotCreationException();
        }

        return customerInquiryService.findCustomerInquiriesByMemberNo(pageable, memberNo);
    }
}

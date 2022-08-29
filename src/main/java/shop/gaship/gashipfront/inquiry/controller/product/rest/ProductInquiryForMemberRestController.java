package shop.gaship.gashipfront.inquiry.controller.product.rest;

import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryAttribute.KEY_PAGE_RESPONSE;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryViewName.VIEW_NAME_PRODUCT_INQUIRY_LIST;

import java.util.Objects;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipfront.exceptions.MemberNotCreationException;
import shop.gaship.gashipfront.inquiry.dto.response.InquiryListResponseDto;
import shop.gaship.gashipfront.inquiry.service.common.CommonInquiryService;
import shop.gaship.gashipfront.inquiry.service.product.ProductInquiryService;
import shop.gaship.gashipfront.security.common.dto.UserDetailsDto;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * @author : 최겸준
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/js/inquiries")
public class ProductInquiryForMemberRestController {

    private final ProductInquiryService productInquiryService;
    private final CommonInquiryService commonInquiryService;

    @GetMapping(value = "/member-self/product-inquiries")
    public PageResponse<InquiryListResponseDto> productInquiryMemberListJs(
        Pageable pageable,
        @AuthenticationPrincipal UserDetailsDto userDetailsDto) {

        Integer memberNo = userDetailsDto.getMemberNo();
        if (Objects.isNull(memberNo)) {
            throw new MemberNotCreationException();
        }

        return productInquiryService.findProductInquiriesByMemberNo(pageable, memberNo);
    }
}

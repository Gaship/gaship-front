package shop.gaship.gashipfront.inquiry.controller.customer;

import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryAttribute.KEY_DETAILS;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryViewName.VIEW_NAME_CUSTOMER_INQUIRY_ADD_FORM;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryViewName.VIEW_NAME_CUSTOMER_INQUIRY_DETAILS;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.gaship.gashipfront.inquiry.dto.response.InquiryDetailsResponseDto;
import shop.gaship.gashipfront.inquiry.service.common.CommonInquiryService;
import shop.gaship.gashipfront.inquiry.util.RoleUserMySelfProcessor;
import shop.gaship.gashipfront.security.common.dto.UserDetailsDto;

/**
 * 상품문의에 대한 요청을 처리하는 클래스입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/inquiries")
public class CustomerInquiryController {

    private final CommonInquiryService commonInquiryService;


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
}

package shop.gaship.gashipfront.inquiry.controller;

import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryAttribute.KEY_SUCCESS_MESSAGE;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryAttribute.VALUE_MESSAGE_INQUIRY_ANSWER_ADD_SUCCESS;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.gaship.gashipfront.inquiry.dto.request.InquiryAnswerRequestDto;
import shop.gaship.gashipfront.inquiry.service.common.CommonInquiryService;
import shop.gaship.gashipfront.security.common.dto.UserDetailsDto;

/**
 * @author : 최겸준
 * @since 1.0
 */
@Controller
@RequestMapping("/inquiries")
@RequiredArgsConstructor
public class CommonInquiryController {
    private final CommonInquiryService commonInquiryService;

    /**
     * 관리자가 문의의 답변을 추가하기 위한 요청을 처리합니다.
     *
     * @param inquiryAnswerAddRequestDto 문의답변에 들어가야할 정보들을 가지는 DTO 객체입니다.
     * @return view 경로를 반환합니다.
     * @author 최겸준
     */
    @PostMapping(value = "/inquiry-answer", params = {"isProduct"})
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN')")
    public String inquiryAnswerAdd(
        @Valid InquiryAnswerRequestDto inquiryAnswerAddRequestDto,
        @NotNull Boolean isProduct,
        @AuthenticationPrincipal UserDetailsDto userDetailsDto,
        RedirectAttributes redirectAttributes) {

        inquiryAnswerAddRequestDto.setEmployeeNo(userDetailsDto.getMemberNo());
        commonInquiryService.addInquiryAnswer(inquiryAnswerAddRequestDto);

        redirectAttributes.addFlashAttribute(KEY_SUCCESS_MESSAGE.getValue(),
            VALUE_MESSAGE_INQUIRY_ANSWER_ADD_SUCCESS.getValue());

        String redirectUri = getRedirectUri(isProduct, Strings.concat("/admin/inquiries/%s-inquiries/" + inquiryAnswerAddRequestDto.getInquiryNo(), "/manager"));
        return Strings.concat("redirect:", redirectUri);
    }

    private String getRedirectUri(Boolean isProduct, String redirectUrlFormat) {
        String type = Boolean.TRUE.equals(isProduct) ? "product" : "customer";

        return String.format(redirectUrlFormat, type);
    }
}

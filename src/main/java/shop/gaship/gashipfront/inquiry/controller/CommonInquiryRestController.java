package shop.gaship.gashipfront.inquiry.controller;

import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryAttribute.KEY_SUCCESS_MESSAGE;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryAttribute.VALUE_MESSAGE_INQUIRY_ANSWER_ADD_SUCCESS;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryAttribute.VALUE_MESSAGE_INQUIRY_ANSWER_DELETE_SUCCESS;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryAttribute.VALUE_MESSAGE_INQUIRY_ANSWER_MODIFY_SUCCESS;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryAttribute.VALUE_MESSAGE_INQUIRY_DELETE_SUCCESS;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.gaship.gashipfront.exceptions.MemberNotCreationException;
import shop.gaship.gashipfront.inquiry.dto.request.InquiryAnswerRequestDto;
import shop.gaship.gashipfront.inquiry.service.common.CommonInquiryService;
import shop.gaship.gashipfront.security.common.dto.UserDetailsDto;

/**
 * 상품문의와 고객문의에 대한 기본적인 등록, 수정, 삭제, 상세조회 처리 요청을 담당하는 클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
@RestController
@RequestMapping("/js/inquiries")
@RequiredArgsConstructor
public class CommonInquiryRestController {

    private final CommonInquiryService commonInquiryService;
    private static final String REDIRECT_URI_FORMAT_MANAGER = "/admin/inquiries/%s-inquiries/";

    /**
     * 관리자가 문의의 답변을 수정하기 위한 요청을 처리합니다.
     *
     * @param inquiryAnswerModifyRequestDto 문의답변에 들어가야할 정보들을 가지는 DTO 객체입니다.
     * @return view 경로를 반환합니다.
     * @author 최겸준
     */
    @PutMapping(value = "/{inquiryNo}/inquiry-answer", params = {"isProduct"})
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN')")
    public String inquiryAnswerModify(@Valid InquiryAnswerRequestDto inquiryAnswerModifyRequestDto,
                                      @NotNull Boolean isProduct,
                                      RedirectAttributes redirectAttributes) {
        commonInquiryService.modifyInquiryAnswer(inquiryAnswerModifyRequestDto);

        redirectAttributes.addFlashAttribute(KEY_SUCCESS_MESSAGE.getValue(),
            VALUE_MESSAGE_INQUIRY_ANSWER_MODIFY_SUCCESS.getValue());

        String redirectUri = getRedirectUri(isProduct, REDIRECT_URI_FORMAT_MANAGER);
        String inquiryNo = inquiryAnswerModifyRequestDto.getInquiryNo().toString();
        return Strings.concat(redirectUri, inquiryNo);
    }

    /**
     * 관리자가 문의의 답변을 삭제하는 기능 요청을 담당합니다.
     *
     * @param inquiryNo 답변을 삭제할 문의의 번호입니다.
     * @return view 경로를 반환합니다.
     * @author 최겸준
     */
    @DeleteMapping(value = "/{inquiryNo}/inquiry-answer", params = {"isProduct"})
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN')")
    public String inquiryAnswerDelete(@PathVariable @NotNull Integer inquiryNo,
                                      @NotNull Boolean isProduct,
                                      RedirectAttributes redirectAttributes) {

        commonInquiryService.deleteInquiryAnswer(inquiryNo);

        redirectAttributes.addFlashAttribute(KEY_SUCCESS_MESSAGE.getValue(),
            VALUE_MESSAGE_INQUIRY_ANSWER_DELETE_SUCCESS.getValue());

        String redirectUri = getRedirectUri(isProduct, REDIRECT_URI_FORMAT_MANAGER);
        return Strings.concat(redirectUri, inquiryNo.toString());
    }

    /**
     * 회원본인에 대한 문의를 삭제하는 기능 요청을 처리합니다.
     *
     * @param inquiryNo 삭제할 문의의 번호입니다.
     * @return view 경로를 반환합니다.
     * @author 최겸준
     */
    @DeleteMapping(value = "/{inquiryNo}", params = {"isProduct"})
    @Secured("ROLE_USER")
    public Map<String, String> inquiryDeleteByMemberSelf(@PathVariable Integer inquiryNo,
                                                         Boolean isProduct,
                                                         RedirectAttributes redirectAttributes,
                                                         @AuthenticationPrincipal UserDetailsDto userDetailsDto) {

        Integer memberNo = userDetailsDto.getMemberNo();
        if (Objects.isNull(memberNo)) {
            throw new MemberNotCreationException();
        }

        commonInquiryService.deleteInquiry(inquiryNo, memberNo);

        redirectAttributes.addFlashAttribute(KEY_SUCCESS_MESSAGE.getValue(),
            VALUE_MESSAGE_INQUIRY_DELETE_SUCCESS.getValue());

        String redirectUri = getRedirectUri(isProduct,
            "/inquiries/member-self/%s-inquiries");
        Map<String, String> json = new HashMap<>();
        json.put("redirectUri", redirectUri);
        return json;
    }

    /**
     * 관리자가 문의를 삭제하는 기능 요청을 처리합니다.
     *
     * @param inquiryNo 삭제할 문의의 번호입니다.
     * @return view 경로를 반환합니다.
     * @author 최겸준
     */
    @DeleteMapping(value = "/{inquiryNo}/manager", params = {"isProduct"})
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN')")
    public Map<String, String> inquiryDeleteByManager(@PathVariable Integer inquiryNo, Boolean isProduct,
                                         RedirectAttributes redirectAttributes) {

        commonInquiryService.deleteInquiryManager(inquiryNo);

        redirectAttributes.addFlashAttribute(KEY_SUCCESS_MESSAGE.getValue(),
            VALUE_MESSAGE_INQUIRY_DELETE_SUCCESS.getValue());

        String redirectUri = getRedirectUri(isProduct, REDIRECT_URI_FORMAT_MANAGER);
        Map<String, String> json = new HashMap<>();
        json.put("redirectUri", redirectUri);
        return json;
    }

    private String getRedirectUri(Boolean isProduct, String redirectUrlFormat) {
        String type = Boolean.TRUE.equals(isProduct) ? "product" : "customer";

        return String.format(redirectUrlFormat, type);
    }
}

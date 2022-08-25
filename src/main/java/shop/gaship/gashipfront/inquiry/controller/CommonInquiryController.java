package shop.gaship.gashipfront.inquiry.controller;

import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryAttribute.KEY_IS_SUCCESS;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryAttribute.KEY_SUCCESS_MESSAGE;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryAttribute.VALUE_MESSAGE_INQUIRY_ANSWER_ADD_SUCCESS;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryAttribute.VALUE_MESSAGE_INQUIRY_ANSWER_DELETE_SUCCESS;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryAttribute.VALUE_MESSAGE_INQUIRY_ANSWER_MODIFY_SUCCESS;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryAttribute.VALUE_MESSAGE_INQUIRY_DELETE_SUCCESS;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import shop.gaship.gashipfront.inquiry.dto.request.InquiryAnswerRequestDto;
import shop.gaship.gashipfront.inquiry.service.common.CommonInquiryService;

/**
 * 상품문의와 고객문의에 대한 기본적인 등록, 수정, 삭제, 상세조회 처리 요청을 담당하는 클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
@Controller
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
    @PostMapping(value = "/inquiries/inquiry-answer", params = {"isProduct"})
    public String inquiryAnswerAdd(@Valid InquiryAnswerRequestDto inquiryAnswerAddRequestDto,
                                   @NotNull Boolean isProduct,
                                   HttpSession session) {
        commonInquiryService.addInquiryAnswer(inquiryAnswerAddRequestDto);

        session.setAttribute(KEY_IS_SUCCESS.getValue(), Boolean.TRUE);
        session.setAttribute(KEY_SUCCESS_MESSAGE.getValue(),
            VALUE_MESSAGE_INQUIRY_ANSWER_ADD_SUCCESS.getValue());

        String redirectUrl = getRedirectUrl(isProduct, "redirect:/inquiries/%s-inquiries/");
        String inquiryNo = inquiryAnswerAddRequestDto.getInquiryNo().toString();
        return Strings.concat(redirectUrl, inquiryNo);
    }

    /**
     * 관리자가 문의의 답변을 수정하기 위한 요청을 처리합니다.
     *
     * @param inquiryAnswerModifyRequestDto 문의답변에 들어가야할 정보들을 가지는 DTO 객체입니다.
     * @return view 경로를 반환합니다.
     * @author 최겸준
     */
    @PutMapping(value = "/inquiries/{inquiryNo}/inquiry-answer", params = {"isProduct"})
    public String inquiryAnswerModify(@Valid InquiryAnswerRequestDto inquiryAnswerModifyRequestDto,
                                      @NotNull Boolean isProduct,
                                      HttpSession session) {
        commonInquiryService.modifyInquiryAnswer(inquiryAnswerModifyRequestDto);

        session.setAttribute(KEY_IS_SUCCESS.getValue(), Boolean.TRUE);
        session.setAttribute(KEY_SUCCESS_MESSAGE.getValue(),
            VALUE_MESSAGE_INQUIRY_ANSWER_MODIFY_SUCCESS.getValue());

        String redirectUrl = getRedirectUrl(isProduct, "redirect:/inquiries/%s-inquiries/");
        String inquiryNo = inquiryAnswerModifyRequestDto.getInquiryNo().toString();
        return Strings.concat(redirectUrl, inquiryNo);
    }

    /**
     * 관리자가 문의의 답변을 삭제하는 기능 요청을 담당합니다.
     *
     * @param inquiryNo 답변을 삭제할 문의의 번호입니다.
     * @return view 경로를 반환합니다.
     * @author 최겸준
     */
    @DeleteMapping(value = "/inquiries/{inquiryNo}/inquiry-answer", params = {"isProduct"})
    public String inquiryAnswerDelete(@PathVariable @NotNull Integer inquiryNo,
                                      @NotNull Boolean isProduct,
                                      HttpSession session) {
        commonInquiryService.deleteInquiryAnswer(inquiryNo);
        session.setAttribute(KEY_IS_SUCCESS.getValue(), Boolean.TRUE);
        session.setAttribute(KEY_SUCCESS_MESSAGE.getValue(),
            VALUE_MESSAGE_INQUIRY_ANSWER_DELETE_SUCCESS.getValue());

        String redirectUrl = getRedirectUrl(isProduct, "redirect:/inquiries/%s-inquiries/");
        return Strings.concat(redirectUrl, inquiryNo.toString());
    }

    /**
     * 회원본인에 대한 문의를 삭제하는 기능 요청을 처리합니다.
     *
     * @param inquiryNo 삭제할 문의의 번호입니다.
     * @return view 경로를 반환합니다.
     * @author 최겸준
     */
    @DeleteMapping(value = "/inquiries/{inquiryNo}", params = {"isProduct"})
    public String inquiryDeleteByMemberSelf(@PathVariable Integer inquiryNo, Boolean isProduct,
                                            HttpSession session) {
        commonInquiryService.deleteInquiry(inquiryNo);

        session.setAttribute(KEY_IS_SUCCESS.getValue(), Boolean.TRUE);
        session.setAttribute(KEY_SUCCESS_MESSAGE.getValue(),
            VALUE_MESSAGE_INQUIRY_DELETE_SUCCESS.getValue());

        return getRedirectUrl(isProduct,
            "redirect:/inquiries/member-self/%s-inquiries");
    }

    /**
     * 관리자가 문의를 삭제하는 기능 요청을 처리합니다.
     *
     * @param inquiryNo 삭제할 문의의 번호입니다.
     * @return view 경로를 반환합니다.
     * @author 최겸준
     */
    @DeleteMapping(value = "/inquiries/{inquiryNo}/manager", params = {"isProduct"})
    public String inquiryDeleteByManager(@PathVariable Integer inquiryNo, Boolean isProduct,
                                         HttpSession session) {
        commonInquiryService.deleteInquiry(inquiryNo);

        session.setAttribute(KEY_IS_SUCCESS.getValue(), Boolean.TRUE);
        session.setAttribute(KEY_SUCCESS_MESSAGE.getValue(),
            VALUE_MESSAGE_INQUIRY_DELETE_SUCCESS.getValue());

        return getRedirectUrl(isProduct, "redirect:/inquiries/%s-inquiries");
    }

    private String getRedirectUrl(Boolean isProduct, String redirectUrlFormat) {
        String type = Boolean.TRUE.equals(isProduct) ? "product" : "customer";

        return String.format(redirectUrlFormat, type);
    }
}

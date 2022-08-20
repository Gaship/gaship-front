package shop.gaship.gashipfront.inquiry.controller;

import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryType.CUSTOMER_INQUIRY;
import static shop.gaship.gashipfront.inquiry.inquiryenum.InquiryType.PRODUCT_INQUIRY;
import static shop.gaship.gashipfront.inquiry.inquiryenum.SuccessAttribute.CUSTOMER_INQUIRY_ADD_SUCCESS;
import static shop.gaship.gashipfront.inquiry.inquiryenum.SuccessAttribute.INQUIRY_ANSWER_ADD_SUCCESS;
import static shop.gaship.gashipfront.inquiry.inquiryenum.SuccessAttribute.INQUIRY_ANSWER_DELETE_SUCCESS;
import static shop.gaship.gashipfront.inquiry.inquiryenum.SuccessAttribute.INQUIRY_ANSWER_MODIFY_SUCCESS;
import static shop.gaship.gashipfront.inquiry.inquiryenum.SuccessAttribute.INQUIRY_DELETE_SUCCESS;
import static shop.gaship.gashipfront.inquiry.inquiryenum.SuccessAttribute.PRODUCT_INQUIRY_ADD_SUCCESS;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.gaship.gashipfront.inquiry.dto.request.InquiryAddRequestDto;
import shop.gaship.gashipfront.inquiry.dto.request.InquiryAnswerRequestDto;
import shop.gaship.gashipfront.inquiry.dto.response.InquiryDetailsResponseDto;
import shop.gaship.gashipfront.inquiry.service.common.CommonInquiryService;

/**
 * 상품문의와 고객문의에 대한 기본적인 등록, 수정, 삭제, 상세조회 처리 요청을 담당하는 클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
@Controller
@RequestMapping("/inquiries")
@RequiredArgsConstructor
public class DefaultInquiryController {

    private final CommonInquiryService commonInquiryService;

    /**
     * 고객문의를 추가하기 위한 요청을 처리합니다.
     *
     * @param inquiryAddRequestDto 해당 server로 온 요청을 바인딩하기위한 DTO 객체입니다.
     * @return view name을 반환합니다.
     * @author 최겸준
     */
    @PostMapping("/customer-inquiry")
    public String customerInquiryAdd(@Valid InquiryAddRequestDto inquiryAddRequestDto,
                                     Model model) {
        inquiryAddRequestDto.setIsProduct(CUSTOMER_INQUIRY.getValue());
        commonInquiryService.addInquiry(inquiryAddRequestDto);

        model.addAttribute("success_attribute", CUSTOMER_INQUIRY_ADD_SUCCESS.getValue());
        return "";
    }

    /**
     * 상품문의를 추가하기 위한 요청을 처리합니다.
     *
     * @param inquiryAddRequestDto 해당 server로 온 요청을 바인딩하기위한 DTO 객체입니다.
     * @return view name을 반환합니다.
     * @author 최겸준
     */
    @PostMapping("/product-inquiry")
    public String productInquiryAdd(@Valid InquiryAddRequestDto inquiryAddRequestDto,
                                    Model model) {
        inquiryAddRequestDto.setIsProduct(PRODUCT_INQUIRY.getValue());
        commonInquiryService.addInquiry(inquiryAddRequestDto);

        model.addAttribute("success_attribute", PRODUCT_INQUIRY_ADD_SUCCESS.getValue());
        return "";
    }

    /**
     * 문의의 답변을 추가하기 위한 요청을 처리합니다.
     *
     * @param inquiryAnswerAddRequestDto 문의답변에 들어가야할 정보들을 가지는 DTO 객체입니다.
     * @return view name을 반환합니다.
     * @author 최겸준
     */
    @PostMapping("/inquiry-answer")
    public String inquiryAnswerAdd(@Valid InquiryAnswerRequestDto inquiryAnswerAddRequestDto,
                                   Model model) {
        commonInquiryService.addInquiryAnswer(inquiryAnswerAddRequestDto);

        model.addAttribute("success_attribute", INQUIRY_ANSWER_ADD_SUCCESS.getValue());
        return "";
    }

    /**
     * 문의의 답변을 수정하기 위한 요청을 처리합니다.
     *
     * @param inquiryAnswerModifyRequestDto 문의답변에 들어가야할 정보들을 가지는 DTO 객체입니다.
     * @return view name을 반환합니다.
     * @author 최겸준
     */
    @PutMapping("/{inquiryNo}/inquiry-answer")
    public String inquiryAnswerModify(@Valid InquiryAnswerRequestDto inquiryAnswerModifyRequestDto,
                                      Model model) {
        commonInquiryService.modifyInquiryAnswer(inquiryAnswerModifyRequestDto);

        model.addAttribute("success_attribute", INQUIRY_ANSWER_MODIFY_SUCCESS.getValue());
        return "";
    }

    /**
     * 문의를 삭제하는 기능 요청을 처리합니다.
     *
     * @param inquiryNo 삭제할 문의의 번호입니다.
     * @return view name을 반환합니다.
     * @author 최겸준
     */
    @DeleteMapping("/{inquiryNo}")
    public String inquiryDelete(@PathVariable Integer inquiryNo, Model model) {
        commonInquiryService.deleteInquiry(inquiryNo);

        model.addAttribute("success_attribute", INQUIRY_DELETE_SUCCESS.getValue());
        return "";
    }

    /**
     * 문의의 답변을 삭제하는 기능 요청을 담당합니다.
     *
     * @param inquiryNo 답변을 삭제할 문의의 번호입니다.
     * @return view name을 반환합니다.
     * @author 최겸준
     */
    @DeleteMapping("/{inquiryNo}/inquiry-answer")
    public String inquiryAnswerDelete(@PathVariable Integer inquiryNo, Model model) {
        commonInquiryService.deleteInquiryAnswer(inquiryNo);

        model.addAttribute("success_attribute", INQUIRY_ANSWER_DELETE_SUCCESS);
        return "";
    }

    /**
     * 문의 상세조회 요청을 처리하는 기능입니다.
     *
     * @param inquiryNo 조회의 기준이 되는 문의번호입니다.
     * @return view name을 반환합니다.
     * @author 최겸준
     */
    @GetMapping(value = "/{inquiryNo}")
    public String inquiryDetails(
        @PathVariable Integer inquiryNo, Model model) {
        InquiryDetailsResponseDto inquiryDetailsResponseDto =
            commonInquiryService.findInquiry(inquiryNo);
        model.addAttribute("inquiry_details", inquiryDetailsResponseDto);

        return "";
    }
}

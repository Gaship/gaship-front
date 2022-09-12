package shop.gaship.gashipfront.inquiry.service.common;

import shop.gaship.gashipfront.inquiry.dto.request.InquiryAddRequestDto;
import shop.gaship.gashipfront.inquiry.dto.request.InquiryAnswerRequestDto;
import shop.gaship.gashipfront.inquiry.dto.response.InquiryDetailsResponseDto;

/**
 * 문의 공통에 관련된 비즈니스 로직을 처리하는 service 클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
public interface CommonInquiryService {

    /**
     * 문의를 등록하기위해서 adapter에 요청전 비지니스로직을 처리하는 기능입니다.
     *
     * @param inquiryAddRequestDto 상품문의 등록에 필요한 정보를 담고있는 DTO 객체입니다.
     * @author 최겸준
     */
    void addInquiry(InquiryAddRequestDto inquiryAddRequestDto);

    /**
     * 문의의 답변을 등록하기위해서 adapter에 요청전 비지니스로직을 처리하는 기능입니다.
     *
     * @param inquiryAnswerAddRequestDto 상품문의 답변 등록에 필요한 정보를 담고 있는 DTO 객체입니다.
     * @author 최겸준
     */
    void addInquiryAnswer(
                          InquiryAnswerRequestDto inquiryAnswerAddRequestDto);

    /**
     * 문의의 답변을 수정하기위해서 adapter에 요청전 비지니스로직을 처리하는 기능입니다.
     *
     * @param inquiryAnswerModifyRequestDto 상품문의 답변 수정에 필요한 정보를 담고 있는 DTO 객체입니다.
     * @author 최겸준
     */
    void modifyInquiryAnswer(
                             InquiryAnswerRequestDto inquiryAnswerModifyRequestDto);

    /**
     * 본인스스로 문의를 실삭제하기위해서 adapter에 요청전 비지니스로직을 처리하는 기능입니다.
     *
     * @param inquiryNo 삭제대상 문의번호입니다.
     * @param memberNo
     * @author 최겸준
     */
    void deleteInquiry(Integer inquiryNo, Integer memberNo);

    /**
     * 직원 또는 관리자가 문의를 실삭제하기위해서 adapter에 요청전 비지니스로직을 처리하는 기능입니다.
     *
     * @param inquiryNo 삭제대상 문의번호입니다.
     * @author 최겸준
     */
    void deleteInquiryManager(Integer inquiryNo);

    /**
     * 문의답변을 삭제하기위해서 adapter에 요청전 비지니스로직을 처리하는 기능입니다.
     *
     * @param inquiryNo 삭제대상 문의번호입니다.
     * @author 최겸준
     */
    void deleteInquiryAnswer(Integer inquiryNo);

    /**
     * 문의를 상세조회하는 기능입니다.
     *
     * @param inquiryNo 조회시 기준이 될 문의번호입니다.
     * @return api서버에서 반환된 문의에 상세에 대한 dto입니다.
     * @author 최겸준
     */
    InquiryDetailsResponseDto findInquiry(Integer inquiryNo);


    InquiryDetailsResponseDto findProductInquiryMemberSelf(Integer inquiryNo);

    InquiryDetailsResponseDto findCustomerInquiryMemberSelf(Integer inquiryNo, Integer memberNo);
}

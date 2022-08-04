package shop.gaship.gashipfront.inquiry.adapter;

import org.springframework.data.domain.Pageable;
import shop.gaship.gashipfront.inquiry.dto.request.InquiryAddRequestDto;
import shop.gaship.gashipfront.inquiry.dto.request.InquiryAnswerRequestDto;
import shop.gaship.gashipfront.inquiry.dto.response.InquiryDetailsResponseDto;
import shop.gaship.gashipfront.inquiry.dto.response.InquiryListResponseDto;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * 문의관련 요청을 webclient로 보내기 위한 adapter 역할을 수행하는 interface입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
public interface InquiryAdapter {

    /**
     * 문의를 추가하기 위한 요청을 보냅니다.
     *
     * @param inquiryDto 요청할때 필요한 정보를 담고있는 DTO 객체입니다.
     * @author 최겸준
     */
    void inquiryAdd(InquiryAddRequestDto inquiryDto);

    /**
     * 문의의 답변을 추가하기 위한 요청을 보냅니다.
     *
     * @param inquiryAnswerAddRequestDto 문의답변 추가요청할때 필요한 정보를 담고있는 DTO 객체입니다.
     * @author 최겸준
     */
    void inquiryAnswerAdd(InquiryAnswerRequestDto inquiryAnswerAddRequestDto);

    /**
     * 문의의 답변을 수정하기 위한 요청을 보냅니다.
     *
     * @param inquiryAnswerModifyRequestDto 문의답변 수정요청할때 필요한 정보를 담고있는 DTO 객체입니다.
     * @author 최겸준
     */
    void inquiryAnswerModify(InquiryAnswerRequestDto inquiryAnswerModifyRequestDto);

    /**
     * 문의를 삭제하는 기능 요청을 보냅니다.
     * 이때 삭제는 실삭제를 뜻합니다.
     *
     * @param inquiryNo 삭제할 문의의 번호입니다.
     * @author 최겸준
     */
    void inquiryDelete(Integer inquiryNo);

    /**
     * 문의의 답변을 삭제하는 기능 요청을 보냅니다.
     * 이때 삭제는 db상의 답변을 null로 변경하는 것을 의미합니다.
     * 정확하게는 문의테이블의 수정이 일어나는 것입니다.
     * 답변변경 외에도 상태와 여러가지 정보를 변경합니다.
     *
     * @param inquiryNo 답변을 삭제할 문의의 번호입니다.
     * @author 최겸준
     */
    void inquiryAnswerDelete(Integer inquiryNo);

    /**
     * 문의 상세조회 요청을 보냅니다.
     *
     * @param inquiryNo 조회의 기준이 되는 문의번호입니다.
     * @author 최겸준
     */
    InquiryDetailsResponseDto inquiryDetails(Integer inquiryNo);

    /**
     * 고객문의 목록을 조회하는 요청을 보냅니다.
     *
     * @param pageable 조회시 페이지네이션에 맞게 요청할때 필요한 정보를 담고있는 객체입니다.
     * @author 최겸준
     */
    PageResponse<InquiryListResponseDto> customerInquiryList(Pageable pageable);

    /**
     * 상품문의 목록을 조회하는 요청을 보냅니다.
     *
     * @param pageable 조회시 페이지네이션에 맞게 요청할때 필요한 정보를 담고있는 객체입니다.
     * @author 최겸준
     */
    PageResponse<InquiryListResponseDto> productInquiryList(Pageable pageable);

    /**
     * 답변대기 상태인 고객문의목록 조회요청을 보냅니다.
     *
     * @param pageable 조회시 페이지네이션에 맞게 요청할때 필요한 정보를 담고있는 객체입니다.
     * @author 최겸준
     */
    PageResponse<InquiryListResponseDto> customerInquiryStatusHoldList(
        Pageable pageable);

    /**
     * 답변완료 상태인 고객문의목록 조회요청을 보냅니다.
     *
     * @param pageable 조회시 페이지네이션에 맞게 요청할때 필요한 정보를 담고있는 객체입니다.
     * @author 최겸준
     */
    PageResponse<InquiryListResponseDto> customerInquiryStatusCompleteList(
        Pageable pageable);

    /**
     * 답변대기 상태인 상품문의목록 조회요청을 보냅니다.
     *
     * @param pageable 조회시 페이지네이션에 맞게 요청할때 필요한 정보를 담고있는 객체입니다.
     * @author 최겸준
     */
    PageResponse<InquiryListResponseDto> productInquiryStatusHoldList(
        Pageable pageable);

    /**
     * 답변완료 상태인 상품문의목록 조회요청을 보냅니다.
     *
     * @param pageable 조회시 페이지네이션에 맞게 요청할때 필요한 정보를 담고있는 객체입니다.
     * @author 최겸준
     */
    PageResponse<InquiryListResponseDto> productInquiryStatusCompleteList(Pageable pageable);

    /**
     * 특정회원에 대한 고객문의목록 조회요청을 보냅니다.
     *
     * @param memberNo 기준이 되는 회원의 식별번호입니다.
     * @param pageable 조회시 페이지네이션에 맞게 요청할때 필요한 정보를 담고있는 객체입니다.
     * @author 최겸준
     */
    PageResponse<InquiryListResponseDto> customerInquiryMemberList(Pageable pageable,
                                                                   Integer memberNo);
    /**
     * 특정회원에 대한 상품문의목록 조회요청을 보냅니다.
     *
     * @param memberNo 기준이 되는 회원의 식별번호입니다.
     * @param pageable 조회시 페이지네이션에 맞게 요청할때 필요한 정보를 담고있는 객체입니다.
     * @author 최겸준
     */
    PageResponse<InquiryListResponseDto> productInquiryMemberList(Pageable pageable,
                                                                  Integer memberNo);
    /**
     * 특정상품에 대한 상품문의목록 조회요청을 보냅니다.
     *
     * @param productNo 기준이 되는 상품의 식별번호입니다.
     * @param pageable 조회시 페이지네이션에 맞게 요청할때 필요한 정보를 담고있는 객체입니다.
     * @author 최겸준
     */
    PageResponse<InquiryListResponseDto> productInquiryProductList(Pageable pageable,
                                                                   Integer productNo);
}

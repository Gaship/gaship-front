package shop.gaship.gashipfront.inquiry.service.customer;

import org.springframework.data.domain.Pageable;
import shop.gaship.gashipfront.inquiry.dto.response.InquiryListResponseDto;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * 고객문의에 대한 비지니스 로직을 처리하는 서비스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
public interface CustomerInquiryService {

    /**
     * 아무 조건없이 모든 고객문의를(목록) 조회하기 위한 기능입니다.
     *
     * @param pageable 페이징 처리를 위한 파라미터입니다.
     * @return PageResponse 객체를 반환합니다.
     * @author 최겸준
     */
    PageResponse<InquiryListResponseDto> findCustomerInquiries(Pageable pageable);

    /**
     * 답변대기상태인 고객문의를 찾는 기능입니다.
     *
     * @param pageable       페이징 처리를 위해 사용합니다.
     * @return PageResponse 객체를 반환합니다.
     * @author 최겸준
     */
    PageResponse<InquiryListResponseDto> findCustomerInquiriesStatusHold(Pageable pageable);

    /**
     * 답변완료상태인 고객문의를 찾는 기능입니다.
     *
     * @param pageable       페이징 처리를 위해 사용합니다.
     * @return PageResponse 객체를 반환합니다.
     * @author 최겸준
     */
    PageResponse<InquiryListResponseDto> findCustomerInquiriesStatusComplete(Pageable pageable);

    /**
     * 특정회원번호를 기준으로 고객문의를 찾는 기능입니다.
     *
     * @param pageable  페이징 처리를 위해 사용합니다.
     * @param memberNo  기준이 될 회원의 번호입니다.
     * @return PageResponse 객체를 반환합니다.
     * @author 최겸준
     */
    PageResponse<InquiryListResponseDto> findInquiriesByMemberNo(Pageable pageable, Integer memberNo);
}

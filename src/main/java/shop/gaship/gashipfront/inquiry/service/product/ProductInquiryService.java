package shop.gaship.gashipfront.inquiry.service.product;

import org.springframework.data.domain.Pageable;
import shop.gaship.gashipfront.inquiry.dto.response.InquiryListResponseDto;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * 상품문의에 대한 비지니스 로직을 처리하는 서비스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
public interface ProductInquiryService {

    /**
     * 아무 조건없이 모든 상품문의 목록을 조회하기 위한 기능입니다.
     *
     * @param pageable 페이징 처리를 위한 파라미터입니다.
     * @return PageResponse 객체를 반환합니다.
     * @author 최겸준
     */
    PageResponse<InquiryListResponseDto> findProductInquiries(Pageable pageable);

    /**
     * 답변대기상태인 상품문의 목록을 찾는 기능입니다.
     *
     * @param pageable       페이징 처리를 위해 사용합니다.
     * @return PageResponse 객체를 반환합니다.
     * @author 최겸준
     */
    PageResponse<InquiryListResponseDto> findProductInquiriesStatusHold(Pageable pageable);

    /**
     * 답변완료상태인 상품문의 목록을 찾는 기능입니다.
     *
     * @param pageable       페이징 처리를 위해 사용합니다.
     * @return PageResponse 객체를 반환합니다.
     * @author 최겸준
     */
    PageResponse<InquiryListResponseDto> findProductInquiriesStatusComplete(Pageable pageable);

    /**
     * 특정회원번호를 기준으로 상품문의 목록을 찾는 기능입니다.
     *
     * @param pageable  페이징 처리를 위해 사용합니다.
     * @param memberNo  기준이 될 회원의 번호입니다.
     * @return PageResponse 객체를 반환합니다.
     * @author 최겸준
     */
    PageResponse<InquiryListResponseDto> findProductInquiriesByMemberNo(Pageable pageable, Integer memberNo);

    /**
     * 특정상품번호를 기준으로 상품문의 목록을 찾는 기능입니다.
     *
     * @param pageable  페이징 처리를 위해 사용합니다.
     * @param productNo  기준이 될 상품의 번호입니다.
     * @return PageResponse 객체를 반환합니다.
     * @author 최겸준
     */
    PageResponse<InquiryListResponseDto> findProductInquiriesByProductNo(Pageable pageable, Integer productNo);
}

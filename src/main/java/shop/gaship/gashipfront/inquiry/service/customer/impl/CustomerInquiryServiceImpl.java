package shop.gaship.gashipfront.inquiry.service.customer.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.gaship.gashipfront.inquiry.adapter.InquiryAdapter;
import shop.gaship.gashipfront.inquiry.dto.response.InquiryListResponseDto;
import shop.gaship.gashipfront.inquiry.service.customer.CustomerInquiryService;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * CustomerInquiryService를 구현한 클래스입니다.
 *
 * @author 최겸준
 * @see shop.gaship.gashipfront.inquiry.service.customer.CustomerInquiryService
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class CustomerInquiryServiceImpl implements CustomerInquiryService {

    private final InquiryAdapter inquiryAdapter;

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponse<InquiryListResponseDto> findCustomerInquiries(Pageable pageable) {

        return inquiryAdapter.customerInquiryList(pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponse<InquiryListResponseDto> findCustomerInquiriesStatusHold(Pageable pageable) {

        return inquiryAdapter.customerInquiryStatusHoldList(pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponse<InquiryListResponseDto> findCustomerInquiriesStatusComplete(
        Pageable pageable) {
        return inquiryAdapter.customerInquiryStatusCompleteList(pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponse<InquiryListResponseDto> findInquiriesByMemberNo(Pageable pageable,
                                                                        Integer memberNo) {
        return inquiryAdapter.customerInquiryMemberList(pageable, memberNo);
    }
}

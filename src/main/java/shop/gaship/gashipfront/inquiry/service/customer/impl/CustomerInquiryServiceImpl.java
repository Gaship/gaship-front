package shop.gaship.gashipfront.inquiry.service.customer.impl;

import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.inquiry.adapter.InquiryAdapter;
import shop.gaship.gashipfront.inquiry.dto.response.InquiryListResponseDto;
import shop.gaship.gashipfront.inquiry.service.customer.CustomerInquiryService;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * CustomerInquiryService 를 구현한 클래스입니다.
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

    @Override
    public PageResponse<InquiryListResponseDto> findCustomerInquiriesAllPrevPage(Pageable pageable,
                                                                                 Integer inquiryNo) {
        return inquiryAdapter.customerInquiryListPrevPage(pageable, inquiryNo);
    }

    @Override
    public PageResponse<InquiryListResponseDto> findCustomerInquiriesAllNextPage(Pageable pageable,
                                                                                 Integer inquiryNo) {
        return inquiryAdapter.customerInquiryListNextPage(pageable, inquiryNo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponse<InquiryListResponseDto> findCustomerInquiriesStatusHold(Pageable pageable) {

        return inquiryAdapter.customerInquiryStatusHoldList(pageable);
    }

    @Override
    public PageResponse<InquiryListResponseDto> findCustomerInquiriesStatusComplete(
        Pageable pageable) {
        return inquiryAdapter.customerInquiryStatusCompleteList(pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponse<InquiryListResponseDto> findCustomerInquiriesStatusCompletePrevPage(
        Pageable pageable, Integer inquiryNo) {
        return inquiryAdapter.customerInquiryStatusCompleteListPrevPage(pageable, inquiryNo);
    }

    @Override
    public PageResponse<InquiryListResponseDto> findCustomerInquiriesStatusCompleteNextPage(
        Pageable pageable, Integer inquiryNo) {
        return inquiryAdapter.customerInquiryStatusCompleteListNextPage(pageable, inquiryNo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponse<InquiryListResponseDto> findCustomerInquiriesByMemberNo(Pageable pageable,
                                                                                Integer memberNo) {

        return inquiryAdapter.customerInquiryMemberList(pageable, memberNo);
    }
}

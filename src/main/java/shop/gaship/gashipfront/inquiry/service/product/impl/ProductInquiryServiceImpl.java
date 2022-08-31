package shop.gaship.gashipfront.inquiry.service.product.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.inquiry.adapter.InquiryAdapter;
import shop.gaship.gashipfront.inquiry.dto.response.InquiryListResponseDto;
import shop.gaship.gashipfront.inquiry.service.product.ProductInquiryService;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * ProductInquiryService 를 구현한 클래스입니다.
 *
 * @author 최겸준
 * @see shop.gaship.gashipfront.inquiry.service.product.ProductInquiryService
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class ProductInquiryServiceImpl implements ProductInquiryService {

    private final InquiryAdapter inquiryAdapter;

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponse<InquiryListResponseDto> findProductInquiries(Pageable pageable) {

        return inquiryAdapter.productInquiryList(pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponse<InquiryListResponseDto> findProductInquiriesStatusHold(Pageable pageable) {

        return inquiryAdapter.productInquiryStatusHoldList(pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponse<InquiryListResponseDto> findProductInquiriesStatusComplete(
        Pageable pageable) {

        return inquiryAdapter.productInquiryStatusCompleteList(pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponse<InquiryListResponseDto> findProductInquiriesByMemberNo(Pageable pageable,
                                                                               Integer memberNo) {

        return inquiryAdapter.productInquiryMemberList(pageable, memberNo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponse<InquiryListResponseDto> findProductInquiriesByProductNo(Pageable pageable,
                                                                                Integer productNo) {


        return inquiryAdapter.productInquiryProductList(pageable, productNo);
    }
}

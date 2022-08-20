package shop.gaship.gashipfront.inquiry.service.product.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.inquiry.adapter.InquiryAdapter;
import shop.gaship.gashipfront.inquiry.dto.response.InquiryListResponseDto;
import shop.gaship.gashipfront.inquiry.service.product.ProductInquiryService;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * @author : 최겸준
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class ProductInquiryServiceImpl implements ProductInquiryService {

    private final InquiryAdapter inquiryAdapter;

    @Override
    public PageResponse<InquiryListResponseDto> findProductInquiries(Pageable pageable) {

        return inquiryAdapter.productInquiryList(pageable);
    }
}

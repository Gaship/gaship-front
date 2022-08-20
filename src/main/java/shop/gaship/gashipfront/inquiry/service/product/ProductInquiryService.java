package shop.gaship.gashipfront.inquiry.service.product;

import org.springframework.data.domain.Pageable;
import shop.gaship.gashipfront.inquiry.dto.response.InquiryListResponseDto;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * @author : 최겸준
 * @since 1.0
 */
public interface ProductInquiryService {

    PageResponse<InquiryListResponseDto> findProductInquiries(Pageable pageable);
}

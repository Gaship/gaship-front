package shop.gaship.gashipfront.productreview.adapter;

import org.springframework.web.multipart.MultipartFile;
import shop.gaship.gashipfront.productreview.dto.request.ProductReviewRequestDto;
import shop.gaship.gashipfront.productreview.dto.response.ProductReviewResponseDto;
import shop.gaship.gashipfront.response.PageResponse;

/**
 * 상품평 어댑터 인터페이스입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
public interface ProductReviewAdapter {
    void productReviewAdd(MultipartFile multipartFile, ProductReviewRequestDto createRequest);

    void productReviewModify(MultipartFile multipartFile, ProductReviewRequestDto modifyRequest);

    void productReviewRemove(Integer orderProductNo);

    ProductReviewResponseDto productReviewDetails(Integer orderProductNo);

    PageResponse<ProductReviewResponseDto> productReviewList();

    PageResponse<ProductReviewResponseDto> productReviewListByProduct(Integer productNo);

    PageResponse<ProductReviewResponseDto> productReviewListByMember(Integer memberNo);
}

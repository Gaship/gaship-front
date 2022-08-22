package shop.gaship.gashipfront.productreview.service.impl;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import shop.gaship.gashipfront.config.ServerConfig;
import shop.gaship.gashipfront.productreview.adapter.ProductReviewAdapter;
import shop.gaship.gashipfront.productreview.dto.request.ProductReviewRequestDto;
import shop.gaship.gashipfront.productreview.dto.response.ProductReviewResponseDto;
import shop.gaship.gashipfront.productreview.service.ProductReviewService;
import shop.gaship.gashipfront.response.PageResponse;

/**
 * 상품평 서비스 구현체 입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class ProductReviewServiceImpl implements ProductReviewService {
    private final ProductReviewAdapter productReviewAdapter;
    private final ServerConfig serverConfig;

    @Override
    public void addReview(MultipartFile multipartFile, ProductReviewRequestDto createRequest) {
        productReviewAdapter.productReviewAdd(multipartFile, createRequest);
    }

    @Override
    public void modifyReview(MultipartFile multipartFile, ProductReviewRequestDto modifyRequest) {
        productReviewAdapter.productReviewModify(multipartFile, modifyRequest);
    }

    @Override
    public ProductReviewResponseDto findReview(Integer orderProductNo) {
        return productReviewAdapter.productReviewDetails(orderProductNo);
    }

    @Override
    public PageResponse<ProductReviewResponseDto> findReviewsByProduct(Integer productNo) {
        PageResponse<ProductReviewResponseDto> reviews =
                productReviewAdapter.productReviewListByProduct(productNo);

        reviews.getContent().forEach(review -> review.setFilePaths(review.getFileNos().stream()
                .map(this::getFilePath)
                .collect(Collectors.toList())));

        return reviews;
    }

    @Override
    public PageResponse<ProductReviewResponseDto> findReviewsByMember(Integer memberNo) {
        PageResponse<ProductReviewResponseDto> reviews =
                productReviewAdapter.productReviewListByMember(memberNo);

        reviews.getContent().forEach(review -> review.setFilePaths(review.getFileNos().stream()
                .map(this::getFilePath)
                .collect(Collectors.toList())));

        return reviews;
    }

    public String getFilePath(Integer fileNo) {
        return UriComponentsBuilder.fromHttpUrl(serverConfig.getGatewayUrl())
                .pathSegment("api/files")
                .pathSegment(String.valueOf(fileNo))
                .pathSegment("download")
                .build().toString();
    }
}

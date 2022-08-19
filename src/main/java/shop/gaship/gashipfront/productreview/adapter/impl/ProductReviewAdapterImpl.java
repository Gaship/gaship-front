package shop.gaship.gashipfront.productreview.adapter.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.productreview.adapter.ProductReviewAdapter;
import shop.gaship.gashipfront.productreview.dto.response.ProductReviewResponseDto;
import shop.gaship.gashipfront.response.PageResponse;

/**
 * 상품평 어댑터 구현체입니다.
 *
 * @author : 김보민
 * @see shop.gaship.gashipfront.productreview.adapter.ProductReviewAdapter
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class ProductReviewAdapterImpl implements ProductReviewAdapter {
    private final WebClient webClient;

    @Override
    public ProductReviewResponseDto productReviewDetails(Integer orderProductNo) {
        return webClient.get()
                .uri("/api/reviews/{orderProductNo}", orderProductNo)
                .retrieve()
                .bodyToMono(ProductReviewResponseDto.class)
                .block();
    }

    @Override
    public PageResponse<ProductReviewResponseDto> productReviewList() {
        return webClient.get()
                .uri("/api/reviews")
                .retrieve()
                .bodyToMono(
                        new ParameterizedTypeReference<PageResponse<ProductReviewResponseDto>>() {
                }).block();
    }

    @Override
    public PageResponse<ProductReviewResponseDto> productReviewListByProduct(Integer productNo) {
        return webClient.get()
                .uri("/api/products/{productNo}/reviews", productNo)
                .retrieve()
                .bodyToMono(
                        new ParameterizedTypeReference<PageResponse<ProductReviewResponseDto>>() {})
                .block();
    }

    @Override
    public PageResponse<ProductReviewResponseDto> productReviewListByMember(Integer memberNo) {
        return webClient.get()
                .uri("/api/members/{memberNo}/reviews", memberNo)
                .retrieve()
                .bodyToMono(
                        new ParameterizedTypeReference<PageResponse<ProductReviewResponseDto>>() {})
                .block();
    }
}

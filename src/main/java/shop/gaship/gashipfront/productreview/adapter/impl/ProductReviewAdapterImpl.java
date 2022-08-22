package shop.gaship.gashipfront.productreview.adapter.impl;

import static org.springframework.http.MediaType.parseMediaType;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.productreview.adapter.ProductReviewAdapter;
import shop.gaship.gashipfront.productreview.dto.request.ProductReviewRequestDto;
import shop.gaship.gashipfront.productreview.dto.response.ProductReviewResponseDto;
import shop.gaship.gashipfront.response.PageResponse;
import shop.gaship.gashipfront.util.ExceptionUtil;

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
    public void productReviewAdd(MultipartFile multipartFile, ProductReviewRequestDto createRequest) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();

        builder.part("image", multipartFile.getResource(),
                parseMediaType(Objects.requireNonNull(multipartFile.getContentType())));
        builder.part("createRequest", createRequest, MediaType.APPLICATION_JSON);

        webClient.post()
                .uri("/api/reviews")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .bodyToMono(Void.class)
                .block();
    }

    @Override
    public void productReviewModify(MultipartFile multipartFile,
                                    ProductReviewRequestDto modifyRequest) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();

        builder.part("image", multipartFile.getResource(),
                parseMediaType(Objects.requireNonNull(multipartFile.getContentType())));
        builder.part("modifyRequest", modifyRequest, MediaType.APPLICATION_JSON);

        webClient.put()
                .uri("/api/reviews/{orderProductNo}", modifyRequest.getOrderProductNo())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .bodyToMono(Void.class)
                .block();
    }

    @Override
    public void productReviewRemove(Integer orderProductNo) {
        webClient.delete()
                .uri("/api/reviews/{orderProductNo}", orderProductNo)
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .bodyToMono(Void.class)
                .block();
    }

    @Override
    public ProductReviewResponseDto productReviewDetails(Integer orderProductNo) {
        return webClient.get()
                .uri("/api/reviews/{orderProductNo}", orderProductNo)
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .bodyToMono(ProductReviewResponseDto.class)
                .block();
    }

    @Override
    public PageResponse<ProductReviewResponseDto> productReviewList() {
        return webClient.get()
                .uri("/api/reviews")
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .bodyToMono(
                        new ParameterizedTypeReference<PageResponse<ProductReviewResponseDto>>() {
                }).block();
    }

    @Override
    public PageResponse<ProductReviewResponseDto> productReviewListByProduct(Integer productNo) {
        return webClient.get()
                .uri("/api/products/{productNo}/reviews", productNo)
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .bodyToMono(
                        new ParameterizedTypeReference<PageResponse<ProductReviewResponseDto>>() {})
                .block();
    }

    @Override
    public PageResponse<ProductReviewResponseDto> productReviewListByMember(Integer memberNo) {
        return webClient.get()
                .uri("/api/members/{memberNo}/reviews", memberNo)
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .bodyToMono(
                        new ParameterizedTypeReference<PageResponse<ProductReviewResponseDto>>() {})
                .block();
    }
}

package shop.gaship.gashipfront.product.adapter.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.product.adapter.ProductAdapter;
import shop.gaship.gashipfront.product.dto.request.ProductCreateRequestDto;
import shop.gaship.gashipfront.product.dto.request.ProductModifyRequestDto;
import shop.gaship.gashipfront.product.dto.request.SalesStatusModifyRequestDto;
import shop.gaship.gashipfront.product.dto.response.ProductAllInfoResponseDto;
import shop.gaship.gashipfront.response.PageResponse;
import shop.gaship.gashipfront.util.ExceptionUtil;

/**
 * 상품 어댑터 구현체입니다.
 *
 * @author : 김보민
 * @see shop.gaship.gashipfront.product.adapter.ProductAdapter
 * @since 1.0
 */
@Component
public class ProductAdapterImpl implements ProductAdapter {
    private static final String REQUEST_URI = "/api/products";
    @Value("${gaship-server.gateway-url}")
    private String gatewayUrl;
    private final WebClient webClient = WebClient.builder()
        .baseUrl(gatewayUrl)
        .build();

    /**
     * {@inheritDoc}
     */
    @Override
    public void productAdd(List<MultipartFile> files, ProductCreateRequestDto createRequest) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        files.forEach(file -> builder.part("image", file));
        builder.part("createRequest", createRequest, MediaType.APPLICATION_JSON);

        webClient.post()
            .uri(REQUEST_URI)
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .bodyValue(builder.build())
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void productModify(List<MultipartFile> files, ProductModifyRequestDto modifyRequest) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        files.forEach(file -> builder.part("image", file));
        builder.part("modifyRequest", modifyRequest, MediaType.APPLICATION_JSON);

        webClient.put()
            .uri(REQUEST_URI + "/{productNo}", modifyRequest.getNo())
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .bodyValue(builder.build())
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void salesStatusModify(SalesStatusModifyRequestDto modifyRequest) {
        webClient.put()
            .uri(REQUEST_URI + "/{productNo}/salesStatus", modifyRequest.getProductNo())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(modifyRequest)
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponse<ProductAllInfoResponseDto> productListSearchCode(String productCode,
                                                                         Pageable pageable) {
        return webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path(REQUEST_URI + "/code")
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .build())
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .bodyToMono(
                new ParameterizedTypeReference<PageResponse<ProductAllInfoResponseDto>>() {
                }
            )
            .block();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductAllInfoResponseDto productDetails(Integer productNo) {
        return webClient.get()
            .uri(REQUEST_URI + "/{productNo}", productNo)
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .bodyToMono(ProductAllInfoResponseDto.class)
            .block();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponse<ProductAllInfoResponseDto> productListSearchStatusCode(String statusName,
                                                                               Pageable pageable) {
        return webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path(REQUEST_URI + "/statusCode")
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .build())
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .bodyToMono(
                new ParameterizedTypeReference<PageResponse<ProductAllInfoResponseDto>>() {
                }
            )
            .block();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponse<ProductAllInfoResponseDto> productAmountList(Long minAmount, Long maxAmount,
                                                                     Pageable pageable) {
        return webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path(REQUEST_URI + "/price")
                .queryParam("min", minAmount)
                .queryParam("max", maxAmount)
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .build())
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .bodyToMono(
                new ParameterizedTypeReference<PageResponse<ProductAllInfoResponseDto>>() {
                }
            )
            .block();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponse<ProductAllInfoResponseDto> productCategoryList(Integer categoryNo,
                                                                       Pageable pageable) {
        return webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path(REQUEST_URI + "/category/{categoryNo}")
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .build(categoryNo))
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .bodyToMono(
                new ParameterizedTypeReference<PageResponse<ProductAllInfoResponseDto>>() {
                }
            )
            .block();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponse<ProductAllInfoResponseDto> productNameList(String name, Pageable pageable) {
        return webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path(REQUEST_URI + "/name")
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .build())
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .bodyToMono(
                new ParameterizedTypeReference<PageResponse<ProductAllInfoResponseDto>>() {
                }
            )
            .block();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponse<ProductAllInfoResponseDto> productListAll(Pageable pageable) {
        return webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path(REQUEST_URI)
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .build())
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .bodyToMono(
                new ParameterizedTypeReference<PageResponse<ProductAllInfoResponseDto>>() {
                }
            )
            .block();
    }

    @Override
    public PageResponse<ProductAllInfoResponseDto> productNosList(List<Integer> productNos,
                                                                  Pageable pageable) {
        return webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path(REQUEST_URI + "/productNos")
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .queryParam("productNos", productNos)
                .build())
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .bodyToMono(
                new ParameterizedTypeReference<PageResponse<ProductAllInfoResponseDto>>() {
                }
            )
            .block();
    }
}

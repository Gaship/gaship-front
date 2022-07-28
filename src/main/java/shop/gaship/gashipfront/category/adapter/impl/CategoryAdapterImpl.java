package shop.gaship.gashipfront.category.adapter.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.category.adapter.CategoryAdapter;
import shop.gaship.gashipfront.category.dto.request.CategoryCreateRequestDto;
import shop.gaship.gashipfront.category.dto.request.CategoryModifyRequestDto;
import shop.gaship.gashipfront.category.dto.response.CategoryResponseDto;
import shop.gaship.gashipfront.util.ExceptionUtil;

/**
 * 카테고리 어댑터 구현체입니다.
 *
 * @author : 김보민
 * @see shop.gaship.gashipfront.category.adapter.CategoryAdapter
 * @since 1.0
 */
@Component
public class CategoryAdapterImpl implements CategoryAdapter {
    @Value("${gaship-server.gateway-url}")
    private String gatewayUrl;

    private static final String REQUEST_URI = "/api/category";

    private final WebClient webClient = WebClient.builder()
            .baseUrl(gatewayUrl)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
    /**
     * {@inheritDoc}
     */
    @Override
    public void categoryAdd(CategoryCreateRequestDto createRequest) {
        webClient.post()
                .uri(REQUEST_URI)
                .bodyValue(createRequest)
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void categoryModify(CategoryModifyRequestDto modifyRequest) {
        webClient.put()
                .uri(REQUEST_URI + "/{categoryNo}", modifyRequest.getNo())
                .bodyValue(modifyRequest)
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CategoryResponseDto categoryDetails(Integer categoryNo) {
        return webClient.get()
                .uri(REQUEST_URI + "/{categoryNo}", categoryNo)
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .bodyToMono(CategoryResponseDto.class)
                .block();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CategoryResponseDto> categoryList() {
        return webClient.get()
                .uri(REQUEST_URI)
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .bodyToFlux(CategoryResponseDto.class)
                .collectList()
                .block();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CategoryResponseDto> lowerCategoryList(Integer categoryNo) {
        return webClient.get()
                .uri(REQUEST_URI + "/{categoryNo}/lower", categoryNo)
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .bodyToFlux(CategoryResponseDto.class)
                .collectList()
                .block();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void categoryRemove(Integer categoryNo) {
        webClient.delete()
                .uri(REQUEST_URI + "/{categoryNo}", categoryNo)
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono);
    }
}

package shop.gaship.gashipfront.category.adapter.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
@RequiredArgsConstructor
public class CategoryAdapterImpl implements CategoryAdapter {
    private final WebClient webClient;
    /**
     * {@inheritDoc}
     */
    @Override
    public void categoryAdd(CategoryCreateRequestDto createRequest) {
        webClient.post()
                .uri("/api/categories")
                .bodyValue(createRequest)
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .bodyToMono(Void.class)
                .block();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void categoryModify(CategoryModifyRequestDto modifyRequest) {
        webClient.put()
                .uri("/api/categories/{categoryNo}", modifyRequest.getNo())
                .bodyValue(modifyRequest)
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .bodyToMono(Void.class)
                .block();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CategoryResponseDto categoryDetails(Integer categoryNo) {
        return webClient.get()
                .uri("/api/categories/{categoryNo}", categoryNo)
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
                .uri("/api/categories")
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
                .uri("/api/categories/{categoryNo}/lower", categoryNo)
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
                .uri("/api/categories/{categoryNo}", categoryNo)
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .bodyToMono(Void.class)
                .block();
    }
}

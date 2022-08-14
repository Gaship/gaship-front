package shop.gaship.gashipfront.tag.adapter.impl;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.security.common.exception.NullResponseBodyException;
import shop.gaship.gashipfront.tag.adapter.TagAdapter;
import shop.gaship.gashipfront.tag.dto.request.TagAddRequestDto;
import shop.gaship.gashipfront.tag.dto.request.TagModifyRequestDto;
import shop.gaship.gashipfront.tag.dto.response.TagResponseDto;
import shop.gaship.gashipfront.util.ExceptionUtil;

import java.util.List;

/**
 * 태그 어뎁터 구현체입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@Component
public class TagAdapterImpl implements TagAdapter {
    private static final String BASE_URL = "http://localhost:7070";

    private final WebClient webClient = WebClient.builder()
            .baseUrl(BASE_URL)
            .build();

    @Override
    public void addTag(TagAddRequestDto request) {
        webClient.post()
                .uri("/api/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .toEntity(void.class)
                .block();
    }

    @Override
    public void modifyTag(TagModifyRequestDto request) {
        webClient.put()
                .uri("/api/tags/{tagNo}", request.getTagNo())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .toEntity(void.class)
                .block();
    }

    @Override
    public TagResponseDto findTag(Long tagNo) {
        return webClient.get()
                .uri("/api/tags/{tagNo}", tagNo)
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .bodyToMono(TagResponseDto.class)
                .blockOptional().orElseThrow(NullResponseBodyException::new);
    }

    @Override
    public List<TagResponseDto> findTags() {
        return webClient.get()
                .uri("/api/tags")
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .bodyToMono(new ParameterizedTypeReference<List<TagResponseDto>>() {
                })
                .blockOptional().orElseThrow(NullResponseBodyException::new);
    }
}
package shop.gaship.gashipfront.tag.adapter;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import shop.gaship.gashipfront.tag.dto.*;

/**
 * packageName    : shop.gaship.gashipfront.repository
 * fileName       : TagAdapter
 * author         : choijungwoo
 * date           : 2022/07/15
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/15        choijungwoo       최초 생성
 */
@Component
public class TagAdapterImpl implements TagAdapter {
    private static final String BASE_URL = "http://localhost:7072";

    private final WebClient webClient = WebClient.builder()
            .baseUrl(BASE_URL)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    @Override
    public Mono<Void> addTag(Integer adminId, TagRegisterRequestDto tagRegisterRequestDto) {
        return webClient.post()
                .uri("/admins/{adminId}/tags", adminId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(tagRegisterRequestDto)
                .retrieve()
                .bodyToMono(Void.class)
                .then();
    }

    @Override
    public Mono<TagResponseDto> findTag(Integer adminId, TagGetRequestDto tagGetRequestDto) {
        return webClient.post()
                .uri("/admins/{adminId}/tags/{tagId}", adminId, tagGetRequestDto.getTagId())
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(TagResponseDto.class);
    }

    @Override
    public void modifyTag(Integer adminId, TagModifyRequestDto tagModifyRequestDto) {
        webClient.post()
                .uri("/admins/{adminId}/tags/{tagId}", adminId, tagModifyRequestDto.getTagId())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(tagModifyRequestDto)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    @Override
    public void removeTag(Integer adminId, TagDeleteRequestDto tagDeleteRequestDto) {
        webClient.post()
                .uri("/admins/{adminId}/tags/{tagId}", adminId, tagDeleteRequestDto.getTagId())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(tagDeleteRequestDto)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    @Override
    public Flux<TagResponseDto> findTags(Integer adminId, TagGetRequestDto tagGetRequestDto, Pageable pageable) {
        UriBuilder uriBuilder = UriComponentsBuilder.newInstance();
        return webClient.post()
                .uri(uriBuilder.path("/admins/{adminId}/tags")
                        .queryParam("page", pageable.getPageNumber())
                        .queryParam("size", pageable.getPageSize())
                        .queryParam("sort", pageable.getSort())
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(TagResponseDto.class);
    }

}

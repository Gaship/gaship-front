package shop.gaship.gashipfront.membertag.adapter.Impl;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.membertag.adapter.MemberTagAdapter;
import shop.gaship.gashipfront.membertag.dto.request.MemberTagRequestDto;
import shop.gaship.gashipfront.membertag.dto.response.MemberTagResponseDto;
import shop.gaship.gashipfront.security.common.exception.NullResponseBodyException;
import shop.gaship.gashipfront.util.ExceptionUtil;

import java.util.List;

/**
 * @author 최정우
 * @since 1.0
 */
@Component
public class MemberTagAdapterImpl implements MemberTagAdapter {
    private static final String BASE_URL = "http://localhost:7070";

    private final WebClient webClient = WebClient.builder()
            .baseUrl(BASE_URL)
            .build();

    @Override
    public void deleteAllAndAddAllMemberTags(MemberTagRequestDto request) {
        webClient.post()
                .uri("/api/members/{memberNo}/member-tag", request.getMemberNo())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .toEntity(void.class)
                .block();
    }

    @Override
    public List<MemberTagResponseDto> findMemberTags(Integer memberNo) {
        return webClient.get()
                .uri("/api/members/{memberNo}/member-tag", memberNo)
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .bodyToMono(new ParameterizedTypeReference<List<MemberTagResponseDto>>() {})
                .blockOptional().orElseThrow(NullResponseBodyException::new);
    }
}

package shop.gaship.gashipfront.membertag.adapter.Impl;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.membertag.adapter.MemberTagAdapter;
import shop.gaship.gashipfront.membertag.dto.request.MemberTagRequestDto;
import shop.gaship.gashipfront.membertag.dto.response.MemberTagResponseDto;
import shop.gaship.gashipfront.util.ExceptionUtil;
import shop.gaship.gashipfront.util.TokenInjectUtil;

/**
 * 회원 태그 어뎁터 서비스 구현체.
 *
 * @author 최정우
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class MemberTagAdapterImpl implements MemberTagAdapter {
    public static final String MEMBER_TAG_PATH = "/api/member-tag";
    private final WebClient webClient;
    private final TokenInjectUtil tokenInjectUtil;

    @Override
    public void deleteAllAndAddAllMemberTags(MemberTagRequestDto request,Integer memberNo) {
        webClient.post()
                .uri(MEMBER_TAG_PATH + "/" + memberNo)
                .headers(tokenInjectUtil.headersConsumer())
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
                .uri("/api/member-tag" + "/" + memberNo)
                .headers(tokenInjectUtil.headersConsumer())
                .retrieve()
                .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
                .bodyToFlux(new ParameterizedTypeReference<MemberTagResponseDto>() {
                })
                .collectList()
                .block();
    }
}

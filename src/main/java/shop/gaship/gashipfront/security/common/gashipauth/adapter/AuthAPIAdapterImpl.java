package shop.gaship.gashipfront.security.common.gashipauth.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.security.common.dto.JwtDto;
import shop.gaship.gashipfront.security.common.dto.UserInfoForJwtRequestDto;
import shop.gaship.gashipfront.security.common.exception.NullResponseBodyException;
import shop.gaship.gashipfront.security.common.util.ExceptionUtil;
import shop.gaship.gashipfront.security.common.gashipauth.adapter.impl.AuthAPIAdapter;

/**
 * AuthAPIAdapter를 구현한 클래스입니다.
 *
 * @author 최겸준
 * @author 조재철
 * @see AuthAPIAdapter
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class AuthAPIAdapterImpl implements AuthAPIAdapter {
    private final WebClient webClient;

    /**
     * {@inheritDoc}
     */
    @Override
    public JwtDto requestJwt(UserInfoForJwtRequestDto detailsDto) {
        return webClient.post()
            .uri("/securities/issue-token")
            .bodyValue(detailsDto)
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .bodyToMono(JwtDto.class)
            .blockOptional()
            .orElseThrow(NullResponseBodyException::new);
    }

    @Override
    public void requestLogout(Integer memberNo, JwtDto jwtDto) {
        webClient.post().uri("/securities/logout")
            .header("X-AUTH-ACCESS-TOKEN", jwtDto.getAccessToken())
            .header("X-AUTH-REFRESH-TOKEN", jwtDto.getRefreshToken())
            .bodyValue(memberNo)
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .toEntity(void.class)
            .blockOptional()
            .orElseThrow();
    }
}

package shop.gaship.gashipfront.security.logout.adaptor.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.security.common.dto.JwtDto;
import shop.gaship.gashipfront.security.common.util.ExceptionUtil;
import shop.gaship.gashipfront.security.logout.adaptor.AuthAdaptor;

/**
 * @author 조재철
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class AuthAdaptorImpl implements AuthAdaptor {
    private final WebClient webClient;

    @Override
    public void logout(Integer memberNo, JwtDto jwtDto) {
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

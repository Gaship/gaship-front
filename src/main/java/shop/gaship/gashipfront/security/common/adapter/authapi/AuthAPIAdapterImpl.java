package shop.gaship.gashipfront.security.common.adapter.authapi;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.security.common.dto.JwtDto;
import shop.gaship.gashipfront.security.common.dto.UserInfoForJwtRequestDto;
import shop.gaship.gashipfront.security.common.exception.NullResponseBodyException;
import shop.gaship.gashipfront.security.common.util.ExceptionUtil;

/**
 * AuthAPIAdapter를 구현한 클래스입니다.
 *
 * @author 최겸준
 * @see shop.gaship.gashipfront.security.common.adapter.authapi.AuthAPIAdapter
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class AuthAPIAdapterImpl implements AuthAPIAdapter {
    private final WebClient webClient;

    /**
     * gaship-auth API에 jwt 토큰을 요청하고 반환받는 기능입니다.
     *
     * @param detailsDto 요청을 보낼때 담아줘야하는 정보객체입니다.
     * @return Jwt jwt 토큰을 반환합니다.
     * @author 최겸준
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
}

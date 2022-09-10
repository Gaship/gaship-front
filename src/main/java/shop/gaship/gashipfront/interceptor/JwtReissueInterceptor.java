package shop.gaship.gashipfront.interceptor;

import java.time.LocalDateTime;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.HandlerInterceptor;
import shop.gaship.gashipfront.aspect.dto.ReissueJwtRequestDto;
import shop.gaship.gashipfront.interceptor.exception.RefreshTokenExpiredException;
import shop.gaship.gashipfront.interceptor.exception.TokenResponseException;
import shop.gaship.gashipfront.security.basic.dto.TokenRequestDto;
import shop.gaship.gashipfront.security.common.dto.JwtDto;

/**
 * @author : 조재철
 * @since 1.0
 */
@RequiredArgsConstructor
@Component
public class JwtReissueInterceptor implements HandlerInterceptor {

    private final WebClient webClient;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        HttpSession session = request.getSession(true);
        JwtDto jwt = (JwtDto) session.getAttribute("jwt");

        if (jwt == null) {
            return true;
        }

        if (jwt.getRefreshTokenExpireDateTime().isBefore(LocalDateTime.now())) {
            throw new RefreshTokenExpiredException();
        }

        if (jwt.getAccessTokenExpireDateTime().isBefore(LocalDateTime.now())) {

            TokenRequestDto tokenRequestDto = (TokenRequestDto) session.getAttribute("memberInfo");

            ReissueJwtRequestDto reissueJwtRequestDto = new ReissueJwtRequestDto();
            reissueJwtRequestDto.setRefreshToken(jwt.getRefreshToken());
            reissueJwtRequestDto.setMemberNo(tokenRequestDto.getMemberNo());
            reissueJwtRequestDto.setAuthorities((List<String>) tokenRequestDto.getAuthorities());

            JwtDto newToken = webClient.post()
                                       .uri(uriBuilder -> uriBuilder.path("/securities/reissue-token").build())
                                       .bodyValue(reissueJwtRequestDto)
                                       .retrieve()
                                       .toEntity(JwtDto.class)
                                       .blockOptional()
                                       .orElseThrow(TokenResponseException::new)
                                       .getBody();

            session.setAttribute("jwt", newToken);
        }

        return true;
    }

}

package shop.gaship.gashipfront.aspect;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.aspect.exception.TokenResponseException;
import shop.gaship.gashipfront.config.ServerConfig;
import shop.gaship.gashipfront.security.common.dto.JwtDto;

/**
 * Jwt Access Token의 만료기한을 체크하여 만료된 토큰일시 재요청 하는 어노테이션.
 *
 * @author : 조재철
 * @since 1.0
 */
@Component
@Aspect
@RequiredArgsConstructor
public class CheckAccessTokenExpireTimeAspect {
    private final ServerConfig serverConfig;

    @Before("@annotation(shop.gaship.gashipfront.aspect.annotation.JwtExpiredCheck)")
    public void checkExpireTime() {
        ServletRequestAttributes requestAttributes =
            (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest req = Objects.requireNonNull(requestAttributes).getRequest();
        HttpSession session = req.getSession(true);
        JwtDto jwt = (JwtDto) session.getAttribute("jwt");

        if (!jwt.getAccessTokenExpireDateTime().isBefore(LocalDateTime.now())) {
            WebClient webClient = WebClient.builder()
                .baseUrl(serverConfig.getGatewayUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

            JwtDto newToken = webClient.post()
                .uri(uriBuilder -> uriBuilder.path("/securities/issue-jwt").build())
                .bodyValue(jwt)
                .retrieve()
                .toEntity(JwtDto.class)
                .blockOptional()
                .orElseThrow(TokenResponseException::new)
                .getBody();

            session.setAttribute("jwt", newToken);
        }
    }
}

package shop.gaship.gashipfront.aspect;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.security.common.dto.JwtDto;

/**
 * Jwt Access Token의 만료기한을 체크하여 만료된 토큰일시 재요청 하는 어노테이션.
 *
 * @author : 조재철
 * @since 1.0
 */
@Aspect
public class CheckAccessTokenExpireTimeAspect {

    @Before("@annotation(shop.gaship.gashipfront.aspect.annotation.JwtExpiredCheck)")
    public void checkExpireTime() {

        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = req.getSession(true);
        JwtDto jwt = (JwtDto) session.getAttribute("jwt");

        if (!jwt.getAccessTokenExpireDateTime().isBefore(LocalDateTime.now())) {
            WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:7070")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

            webClient.post()
                .uri(uriBuilder -> uriBuilder.path("/securities/issue-jwt").build())
                .bodyValue(jwt)
                .retrieve()
                .toEntity(JwtDto.class)
                .block();
        }
    }
}
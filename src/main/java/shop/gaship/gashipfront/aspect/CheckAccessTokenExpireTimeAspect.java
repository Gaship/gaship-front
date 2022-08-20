package shop.gaship.gashipfront.aspect;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.aspect.dto.ReissueJwtRequestDto;
import shop.gaship.gashipfront.aspect.exception.RefreshTokenExpiredException;
import shop.gaship.gashipfront.aspect.exception.TokenResponseException;
import shop.gaship.gashipfront.config.ServerConfig;
import shop.gaship.gashipfront.security.basic.dto.TokenRequestDto;
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
    private final WebClient webClient;
    private final RedisTemplate redisTemplate;
    private final Integer EXPIRE_TIME_THIRTY_MINUTE = 30;

    @Before("@annotation(shop.gaship.gashipfront.aspect.annotation.JwtExpiredCheck)")
    public void checkExpireTime() {
        ServletRequestAttributes requestAttributes =
            (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest req = Objects.requireNonNull(requestAttributes).getRequest();
        HttpSession session = req.getSession(true);
        JwtDto jwt = (JwtDto) session.getAttribute("jwt");

        if (!jwt.getRefreshTokenExpireDateTime().isBefore(LocalDateTime.now())) {
            throw new RefreshTokenExpiredException();
        }

        if (!jwt.getAccessTokenExpireDateTime().isBefore(LocalDateTime.now())) {
            TokenRequestDto tokenRequestDto = (TokenRequestDto) session.getAttribute("memberInfo");

            ReissueJwtRequestDto reissueJwtRequestDto = new ReissueJwtRequestDto();
            reissueJwtRequestDto.setRefreshToken(jwt.getRefreshToken());
            reissueJwtRequestDto.setMemberNo(tokenRequestDto.getMemberNo());
            reissueJwtRequestDto.setAuthorities((List<String>) tokenRequestDto.getAuthorities());

            WebClient webClient = WebClient.builder()
                .baseUrl(serverConfig.getGatewayUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

            JwtDto newToken = webClient.post()
                .uri(uriBuilder -> uriBuilder.path("/securities/reissue-jwt").build())
                .bodyValue(reissueJwtRequestDto)
                .retrieve()
                .toEntity(JwtDto.class)
                .blockOptional()
                .orElseThrow(TokenResponseException::new)
                .getBody();

            session.setAttribute("jwt", newToken);
        }
    }
}

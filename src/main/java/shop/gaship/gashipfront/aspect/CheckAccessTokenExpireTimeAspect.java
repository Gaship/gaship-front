package shop.gaship.gashipfront.aspect;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.security.basic.dto.JwtTokenDto;

/**
 * The type Check access token expire time aspect.
 */
@Aspect
public class CheckAccessTokenExpireTimeAspect {

    @Pointcut("execution(public * shop.gaship.gashipfront.security.controller ..*(..))")
    private void publicTarget() {
    }

    /**
     * @author 조재철
     */
    @Before("publicTarget()")
    public void checkExpireTime() {
        // TODO : 만료 시간이 어디에 담겨있을까용?? 세션??
        LocalDate refreshTokenExpireTime = LocalDate.now().plusDays(1);
        LocalDate accessTokenExpireTime = LocalDate.now().plusDays(1);

        if (!accessTokenExpireTime.isBefore(LocalDate.now()))

        if (!accessTokenExpireTime.isBefore(LocalDate.now())) {
            WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:7070")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

            Map<String, List<String>> headers = new HashMap<>();
            List<String> tokens = List.of(
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzM4NCJ9.eyJyb2xlIjpbIlJPTEVfQURNSU4iXSwiaWQiOjEsImV4cGlyZUF0IjoxNjU4MTUzMzEyNTIzLCJjcmVhdGVBdCI6MTY1ODE1MTUxMjUyMywiZXhwIjoxNjU4MTUzMzEyfQ.O1m3GXRb3mM94lek2w1ZHyfAGJuN4sJ91WWrKTfAufxDUehsmvFAXip-mgZOO5fc",
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzM4NCJ9.eyJyb2xlIjpbIlJPTEVfQURNSU4iXSwiaWQiOjEsImV4cGlyZUF0IjoxNjYwNzgxMjEyNjE4LCJjcmVhdGVBdCI6MTY1ODE1MTUxMjYxOCwiZXhwIjoxNjYwNzgxMjEyfQ.CDo6_rVB5_fLv4NAOcjp6ta0FKSEjsFYviKQmUMuaPwD5w8MtQ6en8Y0IgA3AE55");
            headers.put("X-AUTH-TOKEN", tokens);

            webClient.post()
                .uri(uriBuilder -> uriBuilder.path("/securities/reissueJwt").build())
                .headers(httpHeaders -> {
                    httpHeaders.putAll(headers);
                })
                .body(null) // TODO : 여기 email이 들어와야함 
                .retrieve()
                .toEntity(JwtTokenDto.class)
                .block();

            /** TODO : 저 요청 즉 auth에서 void로 하고 다시 webclient로 client에 쏴주면 안되겟지
             * 해당 dto를 body에 담아서 client에 또 쏴서 그걸 컨트롤러가 받아서 session에 저장을 시키는 식으로 하자!!
             */
        }
    }
}

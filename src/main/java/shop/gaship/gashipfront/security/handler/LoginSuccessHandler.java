package shop.gaship.gashipfront.security.handler;

import java.io.IOException;
import java.time.Duration;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.security.dto.JwtTokenDto;
import shop.gaship.gashipfront.security.dto.SignInSuccessUserDetailsDto;
import shop.gaship.gashipfront.security.dto.TokenRequestDto;
import shop.gaship.gashipfront.util.WebClientUtil;

@RequiredArgsConstructor
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final String gatewayBaseUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication)
            throws ServletException, IOException {
        SignInSuccessUserDetailsDto details =
                (SignInSuccessUserDetailsDto) authentication.getPrincipal();

        TokenRequestDto tokenRequestDto =
                new TokenRequestDto(
                        details.getIdentifyNo(),
                        details.getUsername(),
                        details.getAuthorities()
                );

        JwtTokenDto tokensResponse = WebClient.create(gatewayBaseUrl).post()
                .uri("/securities/issue-token")
                .bodyValue(tokenRequestDto)
                .retrieve()
                .toEntity(JwtTokenDto.class)
                .timeout(Duration.ofSeconds(3))
                .blockOptional()
                .orElseThrow(RuntimeException::new)
                .getBody();

        Cookie accessTokenCookie = new Cookie("accessToken", tokensResponse.getAccessToken());
        response.addCookie(accessTokenCookie);

        super.onAuthenticationSuccess(request, response, authentication);
    }
}

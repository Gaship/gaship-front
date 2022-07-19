package shop.gaship.gashipfront.security.handler;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.exceptions.NoResponseDataException;
import shop.gaship.gashipfront.security.dto.JwtTokenDto;
import shop.gaship.gashipfront.security.dto.SignInUserDetailsDto;
import shop.gaship.gashipfront.security.dto.TokenRequestDto;
import shop.gaship.gashipfront.util.ExceptionUtil;

public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        SignInUserDetailsDto details =
            (SignInUserDetailsDto) authentication.getPrincipal();

        TokenRequestDto tokenRequestDto =
            new TokenRequestDto(
                details.getIdentifyNo(),
                details.getUsername(),
                details.getAuthorities()
            );

        JwtTokenDto tokensResponse = WebClient.create("http://localhost:7070").post()
            .uri("/securities/issue-token")
            .bodyValue(tokenRequestDto)
            .retrieve()
            .onStatus(HttpStatus::isError, ExceptionUtil::createErrorMono)
            .toEntity(JwtTokenDto.class)
            .blockOptional()
            .orElseThrow(() -> new NoResponseDataException(""))
            .getBody();

        HttpSession session = request.getSession(false);

        if(Objects.isNull(session)){
            session = request.getSession();
        }
        // 세션에 토큰 저장
        session.setAttribute("accessToken", tokensResponse.getAccessToken());
        session.setAttribute("refreshToken", tokensResponse.getRefreshToken());
    }
}

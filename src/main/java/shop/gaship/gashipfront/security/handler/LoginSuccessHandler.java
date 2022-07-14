package shop.gaship.gashipfront.security.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import shop.gaship.gashipfront.security.dto.JwtTokenDto;
import shop.gaship.gashipfront.security.dto.SignInSuccessUserDetailsDto;
import shop.gaship.gashipfront.security.dto.TokenRequestDto;
import shop.gaship.gashipfront.util.WebClientUtil;

public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication)
        throws ServletException, IOException {
        SignInSuccessUserDetailsDto details =
            (SignInSuccessUserDetailsDto) authentication.getPrincipal();

        TokenRequestDto toeknRequestDto =
            new TokenRequestDto(
                details.getIdentifyNo(),
                details.getUsername(),
                details.getAuthorities()
            );

        JwtTokenDto toeknsResopnse = new WebClientUtil<JwtTokenDto>().post(
            "http://localhost:7071",
            "/securities/issue-token",
            null,
            null,
            toeknRequestDto,
            JwtTokenDto.class
        ).getBody();

        Cookie accessTokenCookie = new Cookie("accessToken", toeknsResopnse.getAccessToken());
        response.addCookie(accessTokenCookie);

        super.onAuthenticationSuccess(request, response, authentication);
    }
}

package shop.gaship.gashipfront.security.basic.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import shop.gaship.gashipfront.security.basic.dto.TokenRequestDto;
import shop.gaship.gashipfront.security.basic.dto.JwtTokenDto;
import shop.gaship.gashipfront.security.basic.dto.SignInSuccessUserDetailsDto;
import shop.gaship.gashipfront.util.WebClientUtil;

/**
 * @author 조재철
 * @since 1.0
 */
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private static final int THIRTY_MINUTE_AT_MILLI_SECONDS = 1_800;
    private static final int ONE_MONTH_AT_MILLI_SECONDS = 2_629_700;

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

        JwtTokenDto tokensResponse = new WebClientUtil<JwtTokenDto>().post(
            "http://localhost:7071",
            "/securities/issue-token",
            null,
            null,
            tokenRequestDto,
            JwtTokenDto.class
        ).getBody();

        Cookie accessTokenCookie =
            generateTokenCookie("accessToken",
                tokensResponse.getAccessToken(),
                THIRTY_MINUTE_AT_MILLI_SECONDS
            );
        Cookie refreshTokenCookie =
            generateTokenCookie("refreshToken",
            tokensResponse.getRefreshToken(),
            ONE_MONTH_AT_MILLI_SECONDS
        );
        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);

        super.onAuthenticationSuccess(request, response, authentication);
    }

    public Cookie generateTokenCookie(String cookieKeyName, String tokenValue, int expireSeconds) {
        Cookie tokenCookie = new Cookie(cookieKeyName, tokenValue);
        tokenCookie.setHttpOnly(true);
        tokenCookie.setMaxAge(expireSeconds);

        return tokenCookie;
    }
}

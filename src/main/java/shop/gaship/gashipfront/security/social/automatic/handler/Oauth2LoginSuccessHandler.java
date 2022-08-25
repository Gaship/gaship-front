package shop.gaship.gashipfront.security.social.automatic.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import shop.gaship.gashipfront.cart.service.CartService;
import shop.gaship.gashipfront.member.dto.MemberAllFieldDto;
import shop.gaship.gashipfront.member.dto.MemberAllFieldDto;
import shop.gaship.gashipfront.security.basic.dto.TokenRequestDto;
import shop.gaship.gashipfront.security.common.dto.JwtDto;
import shop.gaship.gashipfront.security.common.dto.UserDetailsDto;
import shop.gaship.gashipfront.security.common.gashipauth.service.AuthApiService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Optional;

/**
 * oauth2 기능을 통한 로그인 성공시에 기본적인 처리 및 jwt를 요청하고 session에 추가해주기 위한 클래스입니다.
 *
 * @author : 최겸준
 * @see SavedRequestAwareAuthenticationSuccessHandler
 * @since 1.0
 */

@RequiredArgsConstructor
public class Oauth2LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final AuthApiService commonService;


    /**
     * 로그인 성공시 jwt 토큰을 생성하고 session 또는 redis session에 넣어주는 기능입니다.
     *
     * @author 최겸준
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication)
        throws IOException {

        RequestCache requestCache = new HttpSessionRequestCache();
        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (Objects.nonNull(savedRequest)) {

            String url = savedRequest.getRedirectUrl();
            redirectStrategy.sendRedirect(request, response, url);
        } else {
            redirectStrategy.sendRedirect(request, response, "/");
        }

        UserDetailsDto memberDto = (UserDetailsDto) authentication.getPrincipal();
        List<String> authorityList = memberDto.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

        JwtDto jwt = commonService.getJwt(memberDto.getMemberNo(), authorityList);

        HttpSession session = request.getSession();
        session.setAttribute("jwt", jwt);
    }
}

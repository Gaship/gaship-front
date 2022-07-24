package shop.gaship.gashipfront.security.social.automatic.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import shop.gaship.gashipfront.security.common.dto.JwtDto;
import shop.gaship.gashipfront.security.common.dto.UserDetailsDto;
import shop.gaship.gashipfront.security.common.service.AuthAPIService;
import shop.gaship.gashipfront.security.social.member.dto.MemberDto;

/**
 * oauth2 기능을 통한 로그인 성공시에 기본적인 처리 및 jwt를 요청하고 session에 추가해주기 위한 클래스입니다.
 *
 * @author : 최겸준
 * @see org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler
 * @since 1.0
 */

@RequiredArgsConstructor
public class Oauth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final AuthAPIService commonService;

    /**
     * 로그인 성공시 jwt 토큰을 생성하고 session 또는 redis session에 넣어주는 기능입니다.
     *
     * @author 최겸준
     */
    @SneakyThrows
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        super.onAuthenticationSuccess(request, response, authentication);

        UserDetailsDto memberDto = (UserDetailsDto) authentication.getPrincipal();
        MemberDto member = memberDto.getMember();

        JwtDto jwt = commonService.getJWT(member.getMemberNo(), member.getAuthorities());
        HttpSession session = request.getSession();
        session.setAttribute("jwt", jwt);
    }
}

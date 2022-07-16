package shop.gaship.gashipfront.security.social.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import shop.gaship.gashipfront.security.social.adapter.Adapter;
import shop.gaship.gashipfront.security.social.dto.domain.Member;
import shop.gaship.gashipfront.security.social.dto.jwt.JwtTokenDto;
import shop.gaship.gashipfront.security.social.dto.oauth.UserDetailsDto;
import shop.gaship.gashipfront.security.social.service.common.CommonService;

/**
 * oauth2 기능을 통한 로그인 성공시에 기본적인 처리 및 jwt를 요청하고 session에 추가해주기 위한 클래스입니다.
 *
 * @author : 최겸준
 * @see org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler
 * @since 1.0
 */

@RequiredArgsConstructor
public class Oauth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final CommonService commonService;

    @SneakyThrows
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        super.onAuthenticationSuccess(request, response, authentication);

        UserDetailsDto memberDto = (UserDetailsDto) authentication.getPrincipal();
        Member member = memberDto.getMember();
        // 실제 구동 test를 위한 dummy data
        //        JwtTokenDto jwt = new JwtTokenDto();
        //        jwt.setRefreshToken("this is refresh!!");
        //        jwt.setAccessToken("this is access!!");

        JwtTokenDto jwt = commonService.getJWT(member.getIdentifyNo(), member.getAuthorities());
        HttpSession session = request.getSession();
        session.setAttribute("accessToken", jwt.getAccessToken());
        session.setAttribute("refreshToken", jwt.getRefreshToken());
    }
}

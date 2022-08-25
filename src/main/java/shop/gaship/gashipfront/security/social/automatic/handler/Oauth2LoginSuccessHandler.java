package shop.gaship.gashipfront.security.social.automatic.handler;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import shop.gaship.gashipfront.member.dto.MemberAllFieldDto;
import shop.gaship.gashipfront.security.basic.dto.TokenRequestDto;
import shop.gaship.gashipfront.security.common.dto.JwtDto;
import shop.gaship.gashipfront.security.common.dto.UserDetailsDto;
import shop.gaship.gashipfront.security.common.gashipauth.service.AuthApiService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * oauth2 기능을 통한 로그인 성공시에 기본적인 처리 및 jwt를 요청하고 session에 추가해주기 위한 클래스입니다.
 *
 * @author : 최겸준
 * @see SavedRequestAwareAuthenticationSuccessHandler
 * @since 1.0
 */

@RequiredArgsConstructor
public class Oauth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final AuthApiService commonService;

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
        MemberAllFieldDto member = memberDto.getMember();

        TokenRequestDto tokenRequestDto =
            new TokenRequestDto(
                member.getMemberNo(),
                member.getName(),
                member.getAuthorities()
            );

        JwtDto jwt = commonService.getJwt(member.getMemberNo(), member.getAuthorities());
        HttpSession session = request.getSession();
        session.setAttribute("memberInfo", tokenRequestDto);
        session.setAttribute("jwt", jwt);

    }
}

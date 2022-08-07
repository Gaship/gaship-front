package shop.gaship.gashipfront.security.common.login.controller;

import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import shop.gaship.gashipfront.security.common.dto.JwtDto;
import shop.gaship.gashipfront.security.common.dto.UserDetailsDto;
import shop.gaship.gashipfront.security.common.gashipauth.service.AuthApiService;

/**
 * 로그인 및 로그아웃요청을 처리하는 컨트롤러 클래스입니다.
 *
 * @author 최겸준
 * @author 조재철
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final AuthApiService authApiService;

    /**
     * 로그인 요청을 담당하는 기능입니다.
     *
     * @return 로그인 폼 화면으로 이동할수 있도록 showLoginForm을 반환합니다.
     */
    @GetMapping("/login")
    public String login() {
        return "showLoginForm";
    }

    /**
     * 로그아웃 요청을 담당하는 기능입니다.
     *
     * @param session redis에 저장된 jwt를 불러오기위해 사용합니다.
     */
    @GetMapping("/logout")
    public void logoutProcessing(HttpSession session) {
        JwtDto jwt = (JwtDto) session.getAttribute("jwt");

        SecurityContext context = SecurityContextHolder.getContext();
        UserDetailsDto user = (UserDetailsDto) context.getAuthentication().getPrincipal();
        Integer memberNo = user.getMember().getMemberNo();
        authApiService.logout(memberNo, jwt);

        session.invalidate();
    }
}

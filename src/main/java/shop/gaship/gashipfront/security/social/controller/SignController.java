package shop.gaship.gashipfront.security.social.controller;

import java.util.Objects;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import shop.gaship.gashipfront.security.social.dto.domain.MemberCreationRequest;
import shop.gaship.gashipfront.security.social.service.signup.SignupService;

/**
 * 로그인, 로그아웃, 회원가입을 위한 컨트롤러입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
public class SignController {
    private final SignupService signupService;

    @GetMapping("/login")
    public String login() {
        return "showLoginForm";
    }

    @GetMapping("/signup")
    public String signup() {
        return "showSignupForm";
    }

    @PostMapping("/member")
    public String createMember(MemberCreationRequest memberCreationRequest, HttpSession session) {
        Boolean isSuccess = signupService.createMember(memberCreationRequest);
        session.setAttribute("isSigninSuccess", isSuccess);
        if (!isSuccess) return "redirect:login"; // 렌더링 전에 팝업표시 필요 다시 회원가입 진행해주세요. isSuccess 이용

        // TODO : 만들어진 회원 정보를 이용해서 바로 setContextHolder 를 해줘야함

        return "/"; // 홈페이지 렌더링되기전에 회원가입성공! 하고 login 된채로 있어야함
    }
}

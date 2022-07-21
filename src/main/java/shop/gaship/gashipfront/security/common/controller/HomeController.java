package shop.gaship.gashipfront.security.common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import shop.gaship.gashipfront.security.common.dto.UserDetailsDto;

/**
 * /, all, manager 등 간단한 테스트를 위해서 만들어놓은 url입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "showHome";
    }

    @GetMapping("/all")
    public String all(@AuthenticationPrincipal UserDetailsDto userDetailsDto, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.setAttribute("jordan", "ggoo");
        return "all";
    }

    @GetMapping("/manager")
    public String manager() {
        return "manager";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/login")
    public String login() {
        return "showLoginForm";
    }
}

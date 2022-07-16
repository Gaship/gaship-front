package shop.gaship.gashipfront.security.social.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import shop.gaship.gashipfront.security.social.dto.oauth.UserDetailsDto;

/**
 * packageName    : com.example.demo
 * fileName       : HomeController
 * author         : choi-gyeom-jun
 * date           : 2022-07-12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-07-12        choi-gyeom-jun       최초 생성
 */
@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "showHome";
    }
    @GetMapping("/all")
    public String all(@AuthenticationPrincipal UserDetailsDto userDetailsDto) {
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
        return "showLogin Form";
    }
}

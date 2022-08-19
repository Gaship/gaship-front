package shop.gaship.gashipfront.security.common.home.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import shop.gaship.gashipfront.security.common.dto.UserDetailsDto;

/**
 * 임시 메인 컨트롤러입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
@Controller
public class HomeController {

    @GetMapping("/all")
    public String all(@AuthenticationPrincipal UserDetailsDto userDetailsDto,
                      HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.setAttribute("jordan", "ggoo");
        return "all";
    }

    @GetMapping("/manager")
    public String manager() {
        return "layout/admin/index";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }
}

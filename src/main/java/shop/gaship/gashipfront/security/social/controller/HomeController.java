package shop.gaship.gashipfront.security.social.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * packageName    : shop.gaship.gashipfront.security.social.controller
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
    public String showHome() {
        return "showHome";
    }
}

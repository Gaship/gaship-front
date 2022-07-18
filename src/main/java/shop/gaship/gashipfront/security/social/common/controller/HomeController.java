package shop.gaship.gashipfront.security.social.common.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import shop.gaship.gashipfront.security.social.common.dto.UserDetailsDto;

/**
 * /, all, manager 등 간단한 테스트를 위해서 만들어놓은 url입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
@Controller
public class HomeController {


    /**
     * @return string
     * @author 최겸준
     */
    @GetMapping("/")
    public String home() {
        return "showHome";
    }


    /**
     * @param user    details dto
     * @param request
     * @return string
     * @throws RuntimeException ddd
     * @author 최겸준
     */
    @GetMapping("/all")
    public String all(@AuthenticationPrincipal UserDetailsDto userDetailsDto, HttpServletRequest request) {
        return "all";
    }

    /**
     * * * @author 최겸준
     *
     * @return string
     * @throws 예외사항 작성 ex) FileNotFoundException 지정된 파일을 찾을 수 없습니다
     * @author 최겸준
     */
    @GetMapping("/manager")
    public String manager() {
        return "manager";
    }

    /**
     * * * @author 최겸준
     *
     * @return string
     * @throws 예외사항 작성 ex) FileNotFoundException 지정된 파일을 찾을 수 없습니다
     * @author 최겸준
     */
    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

}

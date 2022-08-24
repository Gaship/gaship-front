package shop.gaship.gashipfront.home;

import java.util.Collection;
import java.util.Objects;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 매니저, 관리자 홈 컨트롤러 클래스입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Controller
@RequestMapping("/manager")
public class ManagerHomeController {
    /**
     * 사용자가 현재 관리자 혹은 직원의 권한이라면 관리자화면을 아니라면 관리자 로그인화면으로
     * 
     * @param principal securityContextHolder에 저장된 사용자의 정보입니다.
     * @return 사용자 주체가 없으면 루트로, 권한이 없다면 관리자 로그인으로, 권한이 있다면 관리자 페이지를 반환합니다.
     */
    @GetMapping
    public String showAdminPage(@AuthenticationPrincipal UserDetails principal){
        if(Objects.isNull(principal)){
            return "redirect:/";
        }

        Collection<? extends GrantedAuthority> authorities = principal.getAuthorities();
        SimpleGrantedAuthority userAuthority = new SimpleGrantedAuthority("ROLE_USER");

        if(principal.getAuthorities().isEmpty() || authorities.contains(userAuthority)) {
            return "redirect:/manager/login";
        }

        return "adminIndex";
    }
}

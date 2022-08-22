package shop.gaship.gashipfront.home;

import java.util.Collection;
import java.util.Objects;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 설명작성란
 *
 * @author 김민수
 * @since 1.0
 */
@Controller
public class ManagerHomeController {
    @GetMapping("/manager")
    public String showAdminPage(@AuthenticationPrincipal UserDetails principal){
        if(Objects.isNull(principal)){
            return "redirect:/";
        }

        Collection<? extends GrantedAuthority> authorities = principal.getAuthorities();
        SimpleGrantedAuthority userAuthority = new SimpleGrantedAuthority("ROLE_USER");

        if(principal.getAuthorities().isEmpty() || authorities.contains(userAuthority)) {
            return "login/adminLogin";
        }

        return "adminIndex";
    }
}

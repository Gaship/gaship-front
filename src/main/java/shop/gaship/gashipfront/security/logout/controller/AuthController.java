package shop.gaship.gashipfront.security.logout.controller;

import javax.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipfront.security.common.dto.JwtDto;
import shop.gaship.gashipfront.security.common.dto.UserDetailsDto;
import shop.gaship.gashipfront.security.common.dto.UserInfoForJwtRequestDto;
import shop.gaship.gashipfront.security.logout.service.AuthService;


/**
 * The type Auth controller.
 */
@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;

//    @GetMapping(value = "/logout")
//    public String logout(HttpSession session) {
//        String csrf = "dsklfj";
//        session.setAttribute("csrf", csrf);
//        return "showLogout";
//    }

    /**
     * @return response entity
     * @author 조재철
     */
    @GetMapping(value = {"/logout", "/dk"})
    public void logoutProcessing(HttpSession session, String csrf) {
        String csrfOrigin = (String) session.getAttribute("session");
        if (csrfOrigin.equals(csrf)) {

        }
        JwtDto jwt = (JwtDto) session.getAttribute("jwt");

        SecurityContext context = SecurityContextHolder.getContext();
        UserDetailsDto user = (UserDetailsDto) context.getAuthentication().getPrincipal();
        Integer memberNo = user.getMember().getMemberNo();
        authService.logout(memberNo, jwt);

        session.invalidate();
    }
}


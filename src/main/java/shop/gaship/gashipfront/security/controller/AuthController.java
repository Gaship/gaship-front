package shop.gaship.gashipfront.security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipfront.security.service.AuthService;


/**
 * @author : 조재철
 * @since 1.0
 */
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    /**
     * @return response entity
     * @author 조재철
     */
    @GetMapping("/logout")
    public ResponseEntity<?> hi() {
        return authService.logout();
    }

    /**
     * @return string
     * @author 조재철
     */
    @GetMapping("/")
    public String hello() {
        return "hello";
    }
}


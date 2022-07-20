package shop.gaship.gashipfront.security.logout.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipfront.security.logout.service.AuthService;


/**
 * The type Auth controller.
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
}


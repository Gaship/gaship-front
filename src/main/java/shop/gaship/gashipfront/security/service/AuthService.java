package shop.gaship.gashipfront.security.service;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import shop.gaship.gashipfront.security.dto.JwtTokenDto;

/**
 * The interface Auth service.
 */
public interface AuthService {

    /**
     * @return response entity
     * @author 조재철
     */
    ResponseEntity<?> logout();
}

package shop.gaship.gashipfront.security.adaptor;

import org.springframework.http.ResponseEntity;
import shop.gaship.gashipfront.security.dto.JwtTokenDto;

/**
 * @author 조재철
 * @since 1.0
 */
public interface AuthAdaptor {

    /**
     * @param jwtTokenDto
     * @return response entity
     * @author 조재철
     */
    ResponseEntity<?> logout(JwtTokenDto jwtTokenDto);
}

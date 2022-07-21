package shop.gaship.gashipfront.security.logout.adaptor;

import org.springframework.http.ResponseEntity;
import shop.gaship.gashipfront.security.common.dto.JwtDto;

/**
 * @author 조재철
 * @since 1.0
 */
public interface AuthAdaptor {

    /**
     * @param jwtDto
     * @return response entity
     * @author 조재철
     */
    ResponseEntity<?> logout(JwtDto jwtDto);
}

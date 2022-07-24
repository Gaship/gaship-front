package shop.gaship.gashipfront.security.logout.service;

import shop.gaship.gashipfront.security.basic.dto.SignInUserDetailsDto;
import shop.gaship.gashipfront.security.common.dto.JwtDto;

/**
 * The interface Auth service.
 */
public interface AuthService {

    /**
     */
    void logout(Integer memberNo, JwtDto jwt);
}

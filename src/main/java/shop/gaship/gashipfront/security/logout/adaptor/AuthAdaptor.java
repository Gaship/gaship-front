package shop.gaship.gashipfront.security.logout.adaptor;

import shop.gaship.gashipfront.security.common.dto.JwtDto;

/**
 * @author 조재철
 * @since 1.0
 */
public interface AuthAdaptor {

    /**
     *
     * @param userDetailsDto
     * @param jwtDto
     * @author 조재철
     */

    void logout(Integer userDetailsDto, JwtDto jwtDto);
}

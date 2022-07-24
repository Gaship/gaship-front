package shop.gaship.gashipfront.security.common.adapter.authapi;

import shop.gaship.gashipfront.security.common.dto.JwtDto;
import shop.gaship.gashipfront.security.common.dto.UserInfoForJwtRequestDto;

/**
 * gaship-auth api에 요청하기위한 adapter 클래스입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
public interface AuthAPIAdapter {
    /**
     * gaship-auth API에 jwt 토큰을 요청하고 반환받는 기능입니다.
     *
     * @param detailsDto 요청을 보낼때 담아줘야하는 정보객체입니다.
     * @return Jwt jwt 토큰을 반환합니다.
     * @author 최겸준
     */
    JwtDto requestJwt(UserInfoForJwtRequestDto detailsDto);
}

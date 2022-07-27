package shop.gaship.gashipfront.security.common.gashipauth.adapter;

import shop.gaship.gashipfront.security.common.dto.JwtDto;
import shop.gaship.gashipfront.security.common.dto.UserInfoForJwtRequestDto;

/**
 * gaship-auth api에 요청하기위한 adapter 클래스입니다.
 *
 * @author 최겸준
 * @author 조재철
 * @since 1.0
 */
public interface AuthApiAdapter {

    /**
     * gaship-auth API에 jwt 토큰을 요청하고 반환받는 기능입니다.
     *
     * @param detailsDto 요청을 보낼때 담아줘야하는 정보객체입니다.
     * @return Jwt jwt 토큰을 반환합니다.
     */
    JwtDto requestJwt(UserInfoForJwtRequestDto detailsDto);

    /**
     * 로그아웃 기능입니다.
     * 로그아웃시에 auth 서버에서 jwt에서 accessToken을 블랙리스트처리하고 refreshToken을 삭제해야하는데 그 요청을 webclient를 통해서 직접 보내는 기능을 담당합니다.
     *
     * @param memberNo 멤버의 고유번호입니다.
     * @param jwtDto auth에서 로그인시 발급해준 토큰입니다.
     */
    void requestLogout(Integer memberNo, JwtDto jwtDto);
}

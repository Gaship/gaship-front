package shop.gaship.gashipfront.security.common.gashipauth.service;

import java.util.List;
import shop.gaship.gashipfront.security.common.dto.JwtDto;

/**
 * 다른 controller에서도 공통으로 사용할만한 기능들을 모아놓는 service입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
public interface AuthApiService {
    /**
     * jwt를 요청하고 반환받는 기능입니다.
     *
     * @param memberNo 멤버의 고유번호입니다.
     * @param authorities 멤버의 권한들입니다.
     * @return 반환받을 Jwt dto 객체입니다.
     */
    JwtDto getJwt(Integer memberNo, List<String> authorities);

    /**
     * 로그아웃 기능입니다.
     * 로그아웃시에 auth 서버에서 jwt에서 accessToken을 블랙리스트처리하고 refreshToken을 삭제해야하는데 그때 처리될수있도록 해당 인자들을 넘겨줍니다.
     *
     * @param memberNo 멤버의 고유번호입니다.
     * @param jwtDto auth에서 로그인시 발급해준 토큰입니다.
     */
    void logout(Integer memberNo, JwtDto jwtDto);
}

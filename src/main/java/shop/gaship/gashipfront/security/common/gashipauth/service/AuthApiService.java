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

    void logout(Integer memberNo, JwtDto jwt);
}

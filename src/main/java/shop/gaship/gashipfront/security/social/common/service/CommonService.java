package shop.gaship.gashipfront.security.social.common.service;

import java.util.List;
import shop.gaship.gashipfront.security.social.common.dto.Jwt;
import shop.gaship.gashipfront.security.social.member.dto.Member;

/**
 * 다른 controller에서도 공통으로 사용할만한 기능들을 모아놓는 service입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
public interface CommonService {
    /**
     * Gets jwt.
     *
     * @param identifyNo  the identify no
     * @param Authorities the authorities
     * @return the jwt
     * @throws Exception the exception
     */
    Jwt getJWT(Integer identifyNo, List<String> Authorities) throws Exception;
}

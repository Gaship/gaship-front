package shop.gaship.gashipfront.security.social.service.common;

import java.util.List;
import shop.gaship.gashipfront.security.social.dto.domain.Member;
import shop.gaship.gashipfront.security.social.dto.jwt.JwtTokenDto;

/**
 * packageName    : shop.gaship.gashipfront.security.social.service.common
 * fileName       : DomainService
 * author         : choi-gyeom-jun
 * date           : 2022-07-14
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-07-14        choi-gyeom-jun       최초 생성
 */
public interface CommonService {
    Member getMemberByEmail(String mobile);

    JwtTokenDto getJWT(Long identifyNo, List<String> Authorities) throws Exception;
}

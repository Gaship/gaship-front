package shop.gaship.gashipfront.security.social.service.common;

import shop.gaship.gashipfront.security.social.dto.domain.Member;

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
public interface ShoppingmallService {
    Member getMember(String mobile);
    String getJWT(String id, String email) throws Exception;
}

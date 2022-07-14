package shop.gaship.gashipfront.security.social.service.common;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.security.social.adapter.Adapter;
import shop.gaship.gashipfront.security.social.dto.domain.Member;

/**
 * packageName    : shop.gaship.gashipfront.security.social.service.common
 * fileName       : ShoppingmallServiceImpl
 * author         : choi-gyeom-jun
 * date           : 2022-07-14
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-07-14        choi-gyeom-jun       최초 생성
 */
@Service
@RequiredArgsConstructor
public class ShoppingmallServiceImpl implements ShoppingmallService {
    private final Adapter adapter;

    @Override
    public Member getMember(String mobile) {
        return adapter.requestMemberByEmail(mobile);
    }

    @Override
    public String getJWT(String id, String email) throws Exception {
        return null;
    }
}

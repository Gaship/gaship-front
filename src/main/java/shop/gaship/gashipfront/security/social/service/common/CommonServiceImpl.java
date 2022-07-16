package shop.gaship.gashipfront.security.social.service.common;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.security.social.adapter.Adapter;
import shop.gaship.gashipfront.security.social.dto.domain.Member;
import shop.gaship.gashipfront.security.social.dto.jwt.JwtTokenDto;
import shop.gaship.gashipfront.security.social.dto.jwt.SignInSuccessUserDetailsDto;

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
public class CommonServiceImpl implements CommonService {
    private final Adapter adapter;

    @Override
    public Member getMember(String email) {
        return adapter.requestMemberByEmail(email);
    }

    @Override
    public JwtTokenDto getJWT(Long identifyNo, List<String> authorities) throws Exception {
        SignInSuccessUserDetailsDto detailsDto = new SignInSuccessUserDetailsDto();
        detailsDto.setIdentifyNo(identifyNo);
        detailsDto.setAuthorities(authorities);

        return adapter.requestJwt(detailsDto);
    }
}

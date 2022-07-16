package shop.gaship.gashipfront.security.social.service.oauth2;

import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.security.social.adapter.Adapter;
import shop.gaship.gashipfront.security.social.dto.domain.Member;
import shop.gaship.gashipfront.security.social.dto.oauth.UserDetailsDto;
import shop.gaship.gashipfront.security.social.service.common.CommonService;

/**
 * packageName    : shop.gaship.gashipfront.security.social.service
 * fileName       : Oauth2UserService
 * author         : choi-gyeom-jun
 * date           : 2022-07-13
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-07-13        choi-gyeom-jun       최초 생성
 */
@Service
@RequiredArgsConstructor
public class Oauth2UserService extends DefaultOAuth2UserService {
    private final CommonService commonService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);

        Map<String, Object> value = (Map<String, Object>) user.getAttributes().get("kakao_account");
        String email = (String) value.get("email");
        Member member = commonService.getMemberByEmail(email);

        // 서버 구동을 통한 test시에 필요한 dummy data
        //        Member member = new Member();
        //        member.setIdentifyNo(1L);
        //        member.setEmail(email);
        //        member.setPassword("1234");
        //        List<String> authorities = new ArrayList<>();
        //        authorities.add("USER");
        //        member.setAuthorities(authorities);

        return new UserDetailsDto(email, member.getPassword(), member.getAuthorities().stream()
            .map(i -> new SimpleGrantedAuthority("ROLE_" + i))
            .collect(Collectors.toList()), member, user.getAttributes());
    }
}

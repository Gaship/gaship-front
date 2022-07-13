package shop.gaship.gashipfront.security.social.service.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.security.social.dto.domain.Member;
import shop.gaship.gashipfront.security.social.dto.domain.UserDetailsDto;

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
public class Oauth2UserService extends DefaultOAuth2UserService {
    @SneakyThrows
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);

        Map<String, Object> value = (Map<String, Object>) user.getAttributes().get("kakao_account");
        String email = (String) value.get("email");

//        Member member = memberRepository.findById(email);
        Member member = new Member();
        List<String> authorities = new ArrayList<>();
        authorities.add("ROLE_" + "USER");
        member.setAuthorities(authorities);
        member.setPassword("1234");
        return new UserDetailsDto(email, member.getPassword(), member.getAuthorities().stream()
            .map(i -> new SimpleGrantedAuthority(i))
            .collect(Collectors.toList()), member, user.getAttributes());
    }
}

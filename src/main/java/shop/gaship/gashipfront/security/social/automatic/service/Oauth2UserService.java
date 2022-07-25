package shop.gaship.gashipfront.security.social.automatic.service;

import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.security.common.dto.UserDetailsDto;
import shop.gaship.gashipfront.security.common.util.SignupManager;
import shop.gaship.gashipfront.security.common.member.dto.MemberDto;
import shop.gaship.gashipfront.security.common.member.service.MemberService;

/**
 * Oauth2를 이용한 로그인시에 사용되어질 class입니다.
 *
 * @author : 최겸준
 * @see org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class Oauth2UserService extends DefaultOAuth2UserService {
    private final MemberService memberService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);

        Map<String, Object> value = (Map<String, Object>) user.getAttributes().get("kakao_account");
        String email = (String) value.get("email");
        SignupManager signupManager = new SignupManager(memberService);
        MemberDto member = signupManager.getMember(email);

        return new UserDetailsDto(email, member.getPassword(), member.getAuthorities().stream()
            .map(i -> new SimpleGrantedAuthority(i))
            .collect(Collectors.toList()), member, user.getAttributes());
    }
}

package shop.gaship.gashipfront.security.social.util;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import shop.gaship.gashipfront.security.common.dto.UserDetailsDto;
import shop.gaship.gashipfront.security.social.member.dto.MemberDto;

/**
 * spring security의 로직이 아닌 수동적인 회원가입이 필요할시를 대비하여 만들어둔 유틸클래스입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
@RequiredArgsConstructor
public class SecurityContextLoginManager {
    /**
     * 로그인 기능입니다.
     *
     * @param member 로그인시켜야할 사용자 정보입니다.
     */
    public static void setSecurityContext(MemberDto member) {
        UserDetailsDto
            userDetailsDto = new UserDetailsDto(member.getEmail(), member.getPassword(), member.getAuthorities().stream()
            .map(i -> new SimpleGrantedAuthority(i))
            .collect(Collectors.toList()), member);

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetailsDto, null,
            userDetailsDto.getAuthorities());
        context.setAuthentication(authentication);
    }
}

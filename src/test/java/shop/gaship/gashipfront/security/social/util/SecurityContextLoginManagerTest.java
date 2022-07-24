package shop.gaship.gashipfront.security.social.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import shop.gaship.gashipfront.security.common.dto.UserDetailsDto;
import shop.gaship.gashipfront.security.social.member.dto.MemberDto;

/**
 * @author : 최겸준
 * @since 1.0
 */
class SecurityContextLoginManagerTest {

    @Test
    void setSecurityContext() throws NoSuchAlgorithmException {
        // given
        MemberDto member = new MemberDto();
        member.setEmail("abc@naver.com");
        member.setPassword("1234");

        List<String> authorities = new ArrayList<>();
        authorities.add("USER");
        member.setAuthorities(authorities);

        UserDetailsDto
            userDetailsDto = new UserDetailsDto(member.getEmail(), member.getPassword(), member.getAuthorities().stream()
            .map(i -> new SimpleGrantedAuthority("ROLE_" + i))
            .collect(Collectors.toList()), member);

        // when
        SecurityContextLoginManager.setSecurityContext(member);

        // then
        SecurityContext context = SecurityContextHolder.getContext();
        UserDetailsDto resultDto = (UserDetailsDto) context.getAuthentication().getPrincipal();

        assertThat(resultDto)
            .isEqualTo(userDetailsDto);
    }
}
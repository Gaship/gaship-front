package shop.gaship.gashipfront.security.basic.dto;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


/**
 * 회원의 로그인 성공시 담기는 데이터 객체입니다.
 *
 * @author : 김민수
 * @since 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SignInSuccessUserDetailsDto implements UserDetails {
    private Long memberNo;
    private String email;
    private String hashedPassword;
    private Boolean isSocial;

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    // 업데이트 하는 객체가 아닌, 로그인 사용자 정보를 주입받기 때문.
    private List<String> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities.stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.hashedPassword;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getMemberNo() {
        return memberNo;
    }

    public Boolean isSocial() {
        return isSocial;
    }
}

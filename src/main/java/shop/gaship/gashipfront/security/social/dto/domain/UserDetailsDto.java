package shop.gaship.gashipfront.security.social.dto.domain;

import java.util.Collection;
import java.util.Map;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * packageName    : shop.gaship.gashipfront.security.social.dto.domain
 * fileName       : UserDetailsVO
 * author         : choi-gyeom-jun
 * date           : 2022-07-13
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-07-13        choi-gyeom-jun       최초 생성
 */

@Getter
public class UserDetailsDto extends User implements OAuth2User {
    private Member member;
    private String email;
    private Map<String, Object> attr;

    public UserDetailsDto(String username, String password,
                          Collection<? extends GrantedAuthority> authorities, Member member, Map<String, Object> attr) {
        this(username, password, authorities, member);
        this.attr = attr;
    }

    public UserDetailsDto(String username, String password,
                          Collection<? extends GrantedAuthority> authorities, Member member) {
        super(username, password, authorities);
        this.email = username;
        this.member = member;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attr;
    }

    @Override
    public String getName() {
        return null;
    }
}

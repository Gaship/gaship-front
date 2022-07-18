package shop.gaship.gashipfront.security.social.common.dto;

import java.util.Collection;
import java.util.Map;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import shop.gaship.gashipfront.security.social.member.dto.Member;

/**
 * Oauth2 또는 OauthDance를 이용한 작업을 수행할때 필요한 Dto 클래스입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
@Getter
public class UserDetailsDto extends User implements OAuth2User {
    private Member member;
    private String email;
    private Map<String, Object> attr;

    /**
     * Instantiates a new User details dto.
     *
     * @param username    the username
     * @param password    the password
     * @param authorities the authorities
     * @param member      the member
     * @param attr        the attr
     */
    public UserDetailsDto(String username, String password,
                          Collection<? extends GrantedAuthority> authorities, Member member, Map<String, Object> attr) {
        this(username, password, authorities, member);
        this.attr = attr;
    }

    /**
     * Instantiates a new User details dto.
     *
     * @param username    the username
     * @param password    the password
     * @param authorities the authorities
     * @param member      the member
     */
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

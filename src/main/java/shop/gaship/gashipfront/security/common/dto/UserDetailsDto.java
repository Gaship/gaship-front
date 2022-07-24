package shop.gaship.gashipfront.security.common.dto;

import java.util.Collection;
import java.util.Map;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import shop.gaship.gashipfront.security.social.member.dto.MemberDto;

/**
 * Oauth2Login 또는 OauthDance를 이용한 작업을 수행할때 필요한 Dto 클래스입니다.
 *
 * @author 최겸준
 * @see org.springframework.security.core.userdetails.User
 * @see org.springframework.security.oauth2.core.user.OAuth2User
 * @since 1.0
 */
@Getter
public class UserDetailsDto extends User implements OAuth2User {
    private MemberDto member;
    private String email;
    private Map<String, Object> attr;

    /**
     * UserDetailsDto 객체의 생성자로서 Oauth2Login 기능을 이용할때 attr 파라미터를 추가로 받습니다.
     *
     * @param username    userDetailsService에서 DB의 Member에 의해 삽입된 email 정보입니다.
     * @param password    userDetailsService에서 DB의 Member에 의해 삽입된 password입니다.
     * @param authorities userDetailsService에서 DB의 Member에 의해 삽입된 권한입니다.
     * @param member      userDetailsService에서 DB의 Member입니다. 위의 3개의 변수 모두 member를 통해서 가져올수있지만 자주 사용하는 접근성이 있어서 필드로 따로 세팅했습니다.
     * @param attr        userDetailsService에서 Oauth2User 객체를 super를 통해 만들고 난다음 getAttributes()로 받아낸 값입니다.
     */
    public UserDetailsDto(String username, String password,
                          Collection<? extends GrantedAuthority> authorities, MemberDto member, Map<String, Object> attr) {
        this(username, password, authorities, member);
        this.attr = attr;
    }

    /**
     * UserDetailsDto 객체의 생성자로서 OauthDance로 직접구현한 기능을 이용할때 해당 생성자로 만듭니다.
     *
     * @param username    userDetailsService에서 DB의 Member에 의해 삽입된 email 정보입니다.
     * @param password    userDetailsService에서 DB의 Member에 의해 삽입된 password입니다.
     * @param authorities userDetailsService에서 DB의 Member에 의해 삽입된 권한입니다.
     * @param member      userDetailsService에서 DB의 Member입니다. 위의 3개의 변수 모두 member를 통해서 가져올수있지만 자주 사용하는 접근성이 있어서 필드로 따로 세팅했습니다.
     */
    public UserDetailsDto(String username, String password,
                          Collection<? extends GrantedAuthority> authorities, MemberDto member) {
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
        return email;
    }
}

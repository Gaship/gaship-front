package shop.gaship.gashipfront.security.social.dto.domain;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member {
    private String email;
    private List<String> authorities;
    private String password;
}

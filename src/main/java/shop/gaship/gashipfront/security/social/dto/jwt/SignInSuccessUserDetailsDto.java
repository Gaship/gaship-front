package shop.gaship.gashipfront.security.social.dto.jwt;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * @author : 최겸준
 * @since 1.0
 */
@Getter
@Setter
public class SignInSuccessUserDetailsDto{
    private Long identifyNo;
    private List<String> authorities;
}
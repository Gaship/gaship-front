package shop.gaship.gashipfront.security.basic.dto;

import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author 조재철
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class TokenRequestDto {
    private final Long identifyNo;
    private final String email;
    private final Collection<? extends GrantedAuthority> authorities;
}

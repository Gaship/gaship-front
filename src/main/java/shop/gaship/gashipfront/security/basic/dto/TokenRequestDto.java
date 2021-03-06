package shop.gaship.gashipfront.security.basic.dto;

import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 조재철
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class TokenRequestDto {
    private final Integer memberNo;
    private final String email;
    private final Collection<String> authorities;
}

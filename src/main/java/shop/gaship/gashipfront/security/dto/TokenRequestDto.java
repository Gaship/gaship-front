package shop.gaship.gashipfront.security.dto;

import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

/**
 * packageName    : shop.gaship.gashipfront.security.dto <br/>
 * fileName       : TokenRequestDto <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/11 <br/>
 * description    : <br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/11           김민수               최초 생성                         <br/>
 */
@Getter
@AllArgsConstructor
public class TokenRequestDto {
    private final Long identifyNo;
    private final String email;
    private final Collection<? extends GrantedAuthority> authorities;
}

package shop.gaship.gashipfront.security.social.dto.jwt;

import lombok.Data;

/**
 * packageName    : shop.gaship.gashipfront.security.dto <br/>
 * fileName       : JwtTokenDto <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/11 <br/>
 * description    : <br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/11           김민수               최초 생성                         <br/>
 */
@Data
public class JwtTokenDto {
    private String accessToken;
    private String refreshToken;
}

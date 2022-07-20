package shop.gaship.gashipfront.security.dto;

import java.time.LocalDate;
import lombok.Data;

/**
 * @author 조재철
 * @since 1.0
 */
@Data
public class JwtTokenExpireTimeDto {
    private final LocalDate accessTokenExpireTime;

}

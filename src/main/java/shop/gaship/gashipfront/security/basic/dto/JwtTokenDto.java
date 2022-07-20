package shop.gaship.gashipfront.security.basic.dto;

import lombok.Data;

/**
 * @author 조재철
 * @since 1.0
 */
@Data
public class JwtTokenDto {
    private String accessToken;
    private String refreshToken;
}
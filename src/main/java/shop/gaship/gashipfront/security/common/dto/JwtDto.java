package shop.gaship.gashipfront.security.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * jwt토큰을 요청한뒤 응답받을때 사용하기위한 dto입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
@Getter
@Setter
public class JwtDto implements Serializable {
    private String accessToken;
    private String refreshToken;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime accessTokenExpireDateTime;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime refreshTokenExpireDateTime;
}

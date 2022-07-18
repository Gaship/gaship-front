package shop.gaship.gashipfront.security.social.dance.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * OauthDance시에 Naver에서 전달해주는 jwt를 받기위한 dto 클래스입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
@NoArgsConstructor
@Getter
@Setter
public class NaverAccessToken {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("expires_in")
    private Integer expiresIn;

    private String error;

    @JsonProperty("error_description")
    private String errorDescription;
}

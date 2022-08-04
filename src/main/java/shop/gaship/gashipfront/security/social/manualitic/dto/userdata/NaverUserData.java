package shop.gaship.gashipfront.security.social.manualitic.dto.userdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Oauth Dance시에 naver로 부터 받아오는 정보들을 저장하기위한 dto클래스입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
@NoArgsConstructor
@Getter
@Setter
public class NaverUserData {
    @JsonProperty("resultcode")
    private String resultCode;

    private String message;

    private NaverUserDataResponse response;
}

package shop.gaship.gashipfront.security.social.dto.userdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class NaverUserData {
    @JsonProperty("resultcode")
    private String resultCode;

    private String message;

    private NaverUserDataResponse response;
}

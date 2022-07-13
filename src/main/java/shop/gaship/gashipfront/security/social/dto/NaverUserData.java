package shop.gaship.gashipfront.security.social.dto;

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

    @JsonProperty("response/id")
    private String id;

    @JsonProperty("response/name")
    private String name;

    @JsonProperty("response/email")
    private String email;

    @JsonProperty("response/gender")
    private String gender;

    @JsonProperty("response/birthyear")
    private String birthYear;

    @JsonProperty("response/birthday")
    private String birthday;

    @JsonProperty("response/mobile")
    private String mobile;
}

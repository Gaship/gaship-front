package shop.gaship.gashipfront.security.social.manualitic.dto.userdata;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Oauth Dance시에 네이버로부터 정보를 받을때 사용자의 세부정보를 저장하는 dto입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
@NoArgsConstructor
@Getter
@Setter
public class NaverUserDataResponse {
    private String id;

    private String name;

    private String email;

    private String gender;

    private String birthyear;

    private String birthday;

    private String phoneNumber;
}

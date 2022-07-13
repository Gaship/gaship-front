package shop.gaship.gashipfront.security.social.dto.userdata;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * packageName    : shop.gaship.gashipfront.security.social.dto
 * fileName       : NaverResponse
 * author         : choi-gyeom-jun
 * date           : 2022-07-12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-07-12        choi-gyeom-jun       최초 생성
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

    private String mobile;
}

package shop.gaship.gashipfront.member.dto.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 회원 정보 수정을 위한 dto.
 *
 * @author 최정우
 * @since 1.0
 */
@Getter
@Setter
public class MemberModifyRequestDto {
    private Integer memberNo;
    private String password;
    private String phoneNumber;
    private String name;
    private String nickname;
    private String gender;
}

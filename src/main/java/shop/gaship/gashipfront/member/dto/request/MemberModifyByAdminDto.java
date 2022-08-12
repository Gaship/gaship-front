package shop.gaship.gashipfront.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원의 정보 수정을 위한 dto.
 *
 * @author 최정우
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberModifyByAdminDto {
    private Integer memberNo;
    private String status;
}

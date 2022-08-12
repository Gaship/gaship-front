package shop.gaship.gashipfront.member.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;

/** 관리자의 멤버 정보 조회 시 사용되는 dto.
 *
 * @author 최정우
 * @since 1.0
 */
@Getter
public class MemberResponseByAdminDto {
    private Integer memberNo;
    private String recommendMemberName;
    private String memberStatus;
    private String memberGrade;
    private String email;
    private String phoneNumber;
    private String nickname;
    private String gender;
    private LocalDate birthDate;
    private Long accumulatePurchaseAmount;
    private LocalDate nextRenewalGradeDate;
    private LocalDateTime registerDatetime;
    private LocalDateTime modifyDatetime;
    private Boolean social;
}

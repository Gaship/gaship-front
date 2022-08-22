package shop.gaship.gashipfront.membergrade.dto.request;

import lombok.Getter;
import lombok.Setter;


/**
 * 회원등급 등록 요청 dto 입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
@Setter
public class MemberGradeAddRequestDto {
    private String name;
    private Long accumulateAmount;
    private Boolean isDefault;
}

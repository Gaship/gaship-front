package shop.gaship.gashipfront.membergrade.dto.request;

import lombok.Getter;

/**
 * 회원등급 수정 요청 dto 입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
public class MemberGradeModifyRequestDto {
    private Integer no;
    private String name;
    private Long accumulateAmount;
    private Boolean isDefault;
}

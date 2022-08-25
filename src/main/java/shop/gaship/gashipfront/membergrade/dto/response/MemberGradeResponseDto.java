package shop.gaship.gashipfront.membergrade.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원등급 조회 요청에 대한 응답 dto 입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
@NoArgsConstructor
public class MemberGradeResponseDto {
    private Integer no;
    private String name;
    private Long accumulateAmount;
    private String renewalPeriodStatusCode;
}

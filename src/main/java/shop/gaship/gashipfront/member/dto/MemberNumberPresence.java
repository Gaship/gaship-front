package shop.gaship.gashipfront.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원 고유번호 정보가 들어간 dto객체입니다.
 *
 * @author : 김민수
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberNumberPresence {
    private Integer memberNo;
}

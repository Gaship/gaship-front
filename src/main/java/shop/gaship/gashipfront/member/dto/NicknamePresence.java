package shop.gaship.gashipfront.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 같은 닉네임이 존재하는지에 대한 결과 여부를 가진 클래스입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NicknamePresence {
    private Boolean hasNickname;
}

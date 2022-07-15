package shop.gaship.gashipfront.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 이메일이 존재하는지에 대한 데이터를 담기위한 dto입니다.
 *
 * @author : 김민수
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class EmailPresence {
    private Boolean hasEmail;
}

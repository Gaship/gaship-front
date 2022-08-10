package shop.gaship.gashipfront.membertag.dto.response;

import lombok.Getter;

/**
 * 회원이 선택한 태그를 반환하는 dto.
 *
 * @author 최정우
 * @since 1.0
 */
@Getter
public class MemberTagResponseDto {
    private Integer tagNo;
    private String title;
}

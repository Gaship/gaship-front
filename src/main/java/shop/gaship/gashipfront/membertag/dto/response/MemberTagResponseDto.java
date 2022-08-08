package shop.gaship.gashipfront.membertag.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 최정우
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class MemberTagResponseDto {
    private final Integer tagNo;
    private final String title;
}

package shop.gaship.gashipfront.tag.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;

/**
 * 태그 단건 조회시에 필요한 값을 담는 dto입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@Getter
public class TagResponseDto {
    private Integer tagNo;
    private String title;
    private LocalDateTime registerDatetime;
    private LocalDateTime modifyDatetime;
}

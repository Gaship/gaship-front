package shop.gaship.gashipfront.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * packageName    : shop.gaship.gashipfront.dto
 * fileName       : TagModifyRequestDto
 * author         : choijungwoo
 * date           : 2022/07/15
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/15        choijungwoo       최초 생성
 */
@Getter
public class TagModifyRequestDto {
    private Integer tagId;
    private String title;

    @Builder
    public TagModifyRequestDto(Integer tagId, String title) {
        this.tagId = tagId;
        this.title = title;
    }
}

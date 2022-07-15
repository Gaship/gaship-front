package shop.gaship.gashipfront.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * packageName    : shop.gaship.gashipfront.dto
 * fileName       : TagDeleteRequestDto
 * author         : choijungwoo
 * date           : 2022/07/15
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/15        choijungwoo       최초 생성
 */
@Getter
public class TagDeleteRequestDto {
    private Integer tagId;
    private String title;

    @Builder
    public TagDeleteRequestDto(Integer tagId, String title) {
        this.tagId = tagId;
        this.title = title;
    }
}

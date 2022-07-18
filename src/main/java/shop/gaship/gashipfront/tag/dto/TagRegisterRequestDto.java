package shop.gaship.gashipfront.tag.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * packageName    : shop.gaship.gashipfront.dto
 * fileName       : TagRegisterRequestDto
 * author         : choijungwoo
 * date           : 2022/07/15
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/15        choijungwoo       최초 생성
 */
@Getter
public class TagRegisterRequestDto {
    private String title;

    @Builder
    public TagRegisterRequestDto(String title) {
        this.title = title;
    }
}

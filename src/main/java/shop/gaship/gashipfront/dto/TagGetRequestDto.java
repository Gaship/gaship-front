package shop.gaship.gashipfront.dto;

import lombok.Getter;

/**
 * packageName    : shop.gaship.gashipfront.dto
 * fileName       : TagGetRequestDto
 * author         : choijungwoo
 * date           : 2022/07/15
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/15        choijungwoo       최초 생성
 */
@Getter
public class TagGetRequestDto {
    private Integer tagId;
    private String title;
}

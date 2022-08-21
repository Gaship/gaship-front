package shop.gaship.gashipfront.category.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 카테고리 조회 응답 시 담기는 데이터 객체입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@Getter
@NoArgsConstructor
public class CategoryResponseDto {
    private Integer no;
    private String name;
    private Integer level;
    private Integer upperCategoryNo;
    private String upperCategoryName;
    private List<CategoryResponseDto> lowerCategories;
}

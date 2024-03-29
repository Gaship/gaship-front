package shop.gaship.gashipfront.category.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

/**
 * 카테고리 생성 요청에 담기는 데이터 객체입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@AllArgsConstructor
@Getter
public class CategoryCreateRequestDto {
    @NotBlank(message = "카테고리 이름은 필수 입력 값입니다.")
    @Length(max = 20, message = "카테고리 이름은 20자 이하여야 합니다.")
    private String name;

    @Min(1)
    private Integer upperCategoryNo;
}

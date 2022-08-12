package shop.gaship.gashipfront.tag.dto.request;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

/**
 * 태그 수정에 쓰이는 dto 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@Getter
public class TagModifyRequestDto {
    @NotNull(message = "등록하려는 태그의 tagNo 는 필수값입니다.")
    private Integer tagNo;

    @NotNull(message = "변경하려는 태그의 title 은 필수값입니다.")
    @Length(min = 1, max = 10, message = "title 의 길이는 최소 1 이상 최대 10 이하 입니다.")
    private String title;
}

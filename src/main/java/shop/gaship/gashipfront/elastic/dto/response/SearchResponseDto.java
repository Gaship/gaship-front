package shop.gaship.gashipfront.elastic.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 검색을 통해 얻어지는 값들이 담깁니다.
 *
 * @author : 유호철
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SearchResponseDto {
    private Integer id;
    private String name;
    private String code;
}

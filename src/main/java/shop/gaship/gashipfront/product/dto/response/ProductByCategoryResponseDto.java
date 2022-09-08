package shop.gaship.gashipfront.product.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 설명작성란
 *
 * @author : 유호철
 * @since 1.0
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductByCategoryResponseDto {
    private Integer productNo;
    private String productName;
    private Long amount;
    private List<String> filePaths;
}

package shop.gaship.gashipfront.inquiry.dto.request.view;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * request parameter로 view에서 상품번호와 이름이 넘어왔을때 검증하고 값을 옮기는 클래스입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
@Getter
@Setter
public class ProductInfo {

    @Min(value = 1, message = "상품번호는 최소값이 1입니다.")
    @NotNull(message = "상품번호는 필수값입니다.")
    private Integer productNo;

    @NotBlank(message = "상품이름은 필수값입니다.")
    private String productName;
}

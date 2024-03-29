package shop.gaship.gashipfront.product.dto.request;

import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;


/**
 * 상품 생성 요청 dto 클래스입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class ProductCreateRequestDto {
    @Min(1)
    @NotNull
    private Integer categoryNo;

    @Min(1)
    @NotNull
    private Integer deliveryTypeNo;

    @Length(max = 100, message = "상품 이름은 100자 이하여야 합니다.")
    @NotBlank
    private String name;

    @Min(value = 0, message = "금액은 0보다 커야 합니다.")
    @NotNull
    private Long amount;

    @Length(max = 20, message = "제조사는 20자 이하여야 합니다.")
    @NotBlank
    private String manufacturer;

    @Length(max = 20, message = "제조국은 20자 이하여야 합니다.")
    @NotBlank
    private String manufacturerCountry;

    @Length(max = 20, message = "판매자는 20자 이하여야 합니다.")
    @NotBlank
    private String seller;

    @Length(max = 20, message = "수입자는 20자 이하여야 합니다.")
    @NotBlank
    private String importer;

    @Min(value = 0, message = "금액은 0 이상이어야 합니다.")
    @NotNull
    private Long shippingInstallationCost;

    @Length(max = 255, message = "품질보증기준은 255자 이하여야 합니다.")
    @NotBlank
    private String qualityAssuranceStandard;

    @NotBlank
    private String color;

    @Min(value = 0, message = "재고량은 0 이상이여야 합니다.")
    @NotNull
    private Integer stockQuantity;

    @NotNull
    private String explanation;

    @NotNull
    @NotEmpty(message = "태그는 비어있을수 없습니다.")
    private List<Integer> tagNos;

    @Length(max = 100, message = "제품코드는 100자 이하여야 합니다.")
    @NotBlank
    private String code;

}

package shop.gaship.gashipfront.addresslocal.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;

/**
 * 설명작성란
 *
 * @author : 유호철
 * @since 1.0
 */

@Getter
public class AddressLocalModifyRequestDto {

    @Min(1)
    @NotNull(message = "지역을 입력하세요")
    private Integer localNo;
    @NotNull(message = "가능여부를 입력하세요")
    private boolean isDelivery;
}

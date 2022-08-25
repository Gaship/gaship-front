package shop.gaship.gashipfront.addresslocal.dto.response;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 하위지역을 반환받기위한 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddressSubLocalResponseDto {
    @Min(1)
    @NotNull
    private Integer addressNo;
    @NotBlank
    private String addressName;

    private Boolean isDelivery;
}

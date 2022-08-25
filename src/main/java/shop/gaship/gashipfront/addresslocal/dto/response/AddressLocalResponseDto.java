package shop.gaship.gashipfront.addresslocal.dto.response;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 지역정보에대한 값이 반환되는 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddressLocalResponseDto {

    @Min(0)
    @NotNull
    private Integer addressNo;
    @NotBlank
    private String addressName;
    private boolean allowDelivery;

}

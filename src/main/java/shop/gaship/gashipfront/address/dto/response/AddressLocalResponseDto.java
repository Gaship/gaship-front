package shop.gaship.gashipfront.address.dto.response;

import javax.validation.constraints.NotBlank;
import lombok.Getter;

/**
 * 지역정보에대한 값이 반환되는 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */

@Getter
public class AddressLocalResponseDto {

    @NotBlank
    private String upperAddressName;
    @NotBlank
    private String addressName;
}

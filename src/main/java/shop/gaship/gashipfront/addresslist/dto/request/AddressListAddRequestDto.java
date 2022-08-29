package shop.gaship.gashipfront.addresslist.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 배송지목록 추가 dto.
 *
 * @author 최정우
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddressListAddRequestDto {
    private Integer addressLocalNo;
    private Integer memberNo;
    private String address;
    private String addressDetail;
    private String zipCode;
}

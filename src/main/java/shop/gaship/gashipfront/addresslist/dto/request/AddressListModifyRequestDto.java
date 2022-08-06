package shop.gaship.gashipfront.addresslist.dto.request;

import lombok.Getter;

/**
 * 배송지목록 수정을 위한 dto
 * @author 최정우
 * @since 1.0
 */
@Getter
public class AddressListModifyRequestDto {
    private Integer addressListNo;
    private Integer addressLocalNo;
    private Integer memberNo;
    private String address;
    private String addressDetail;
    private String zipCode;
}

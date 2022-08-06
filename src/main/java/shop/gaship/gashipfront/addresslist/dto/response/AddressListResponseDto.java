package shop.gaship.gashipfront.addresslist.dto.response;

import lombok.Getter;

/**
 * @author 최정우
 * @since 1.0
 */
@Getter
public class AddressListResponseDto {
    private Integer addressListNo;
    private String addressName;
    private boolean allowDelivery;
    private String address;
    private String addressDetail;
    private String zipCode;
}

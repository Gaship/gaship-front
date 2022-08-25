package shop.gaship.gashipfront.addresslocal.service;

import java.util.List;
import shop.gaship.gashipfront.addresslocal.dto.response.AddressLocalResponseDto;
import shop.gaship.gashipfront.addresslocal.dto.response.AddressSubLocalResponseDto;

/**
 * 설명작성란
 *
 * @author : 유호철
 * @since 1.0
 */
public interface AddressLocalService {

    boolean modifyAddressLocal(Integer localNo, boolean isDelivery);

    List<AddressSubLocalResponseDto> addressSubList(String address);

    List<AddressLocalResponseDto> addressList();
}

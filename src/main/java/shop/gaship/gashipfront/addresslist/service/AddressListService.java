package shop.gaship.gashipfront.addresslist.service;

import org.springframework.data.domain.Pageable;
import shop.gaship.gashipfront.addresslist.dto.request.AddressListAddRequestDto;
import shop.gaship.gashipfront.addresslist.dto.request.AddressListModifyRequestDto;

/**
 * @author 최정우
 * @since 1.0
 */
public interface AddressListService {
    void addAddressList(AddressListAddRequestDto request);

    void modifyAddressList(AddressListModifyRequestDto request);

    void deleteAddressList(Long addressListNo);

    Object findAddressList(Long addressListNo);

    Object findAddressLists(Pageable pageable);
}

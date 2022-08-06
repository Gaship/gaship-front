package shop.gaship.gashipfront.addresslist.adapter;

import org.springframework.data.domain.Pageable;
import shop.gaship.gashipfront.addresslist.dto.request.AddressListAddRequestDto;
import shop.gaship.gashipfront.addresslist.dto.request.AddressListModifyRequestDto;
import shop.gaship.gashipfront.addresslist.dto.response.AddressListResponseDto;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * @author 최정우
 * @since 1.0
 */
public interface AddressListAdapter {
    void addAddressList(AddressListAddRequestDto request);

    void modifyAddressList(AddressListModifyRequestDto request);

    void deleteAddressList(Long addressListNo);

    AddressListResponseDto findAddressList(Long addressListNo);

    PageResponse<AddressListResponseDto> findAddressLists(String memberNo, Pageable pageable);
}

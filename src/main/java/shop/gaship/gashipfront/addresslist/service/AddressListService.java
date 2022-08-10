package shop.gaship.gashipfront.addresslist.service;

import org.springframework.data.domain.Pageable;
import shop.gaship.gashipfront.addresslist.dto.request.AddressListAddRequestDto;
import shop.gaship.gashipfront.addresslist.dto.request.AddressListModifyRequestDto;
import shop.gaship.gashipfront.addresslist.dto.response.AddressListResponseDto;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * 배송지목록의 서비스 인터페이스.
 *
 * @author 최정우
 * @since 1.0
 */
public interface AddressListService {
    void addAddressList(AddressListAddRequestDto request);

    void modifyAddressList(AddressListModifyRequestDto request);

    void deleteAddressList(Long memberNo, Long addressListNo);

    AddressListResponseDto findAddressList(Long memberNo, Long addressListNo);

    PageResponse<AddressListResponseDto> findAddressLists(Long memberNo, Pageable pageable);
}

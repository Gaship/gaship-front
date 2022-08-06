package shop.gaship.gashipfront.addresslist.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.addresslist.dto.request.AddressListAddRequestDto;
import shop.gaship.gashipfront.addresslist.dto.request.AddressListModifyRequestDto;
import shop.gaship.gashipfront.addresslist.dto.response.AddressListResponseDto;
import shop.gaship.gashipfront.addresslist.service.AddressListService;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * 배송지목록의 서비스 구현체.
 *
 * @author 최정우
 * @since 1.0
 */

@Service
@RequiredArgsConstructor
public class AddressListServiceImpl implements AddressListService {
    @Override
    public void addAddressList(AddressListAddRequestDto request) {

    }

    @Override
    public void modifyAddressList(AddressListModifyRequestDto request) {

    }

    @Override
    public void deleteAddressList(Long addressListNo) {

    }

    @Override
    public AddressListResponseDto findAddressList(Long addressListNo) {
        return null;
    }

    @Override
    public PageResponse<AddressListResponseDto> findAddressLists(String memberNo, Pageable pageable) {
        return null;
    }
}

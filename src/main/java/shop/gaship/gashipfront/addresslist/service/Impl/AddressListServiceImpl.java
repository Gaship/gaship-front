package shop.gaship.gashipfront.addresslist.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.addresslist.adapter.AddressListAdapter;
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
    private final AddressListAdapter addressListAdapter;

    /**
     * {@inheritDoc}
     */
    @Override
    public void addAddressList(AddressListAddRequestDto request) {
        addressListAdapter.addAddressList(request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void modifyAddressList(AddressListModifyRequestDto request) {
        addressListAdapter.modifyAddressList(request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteAddressList(Long memberNo, Long addressListNo) {
        addressListAdapter.deleteAddressList(memberNo, addressListNo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AddressListResponseDto findAddressList(Long memberNo, Long addressListNo) {
        return addressListAdapter.findAddressList(memberNo, addressListNo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponse<AddressListResponseDto> findAddressLists(Long memberNo, Pageable pageable) {
        return addressListAdapter.findAddressLists(memberNo, pageable);
    }
}

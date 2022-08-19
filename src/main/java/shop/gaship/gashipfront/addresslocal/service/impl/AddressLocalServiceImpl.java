package shop.gaship.gashipfront.addresslocal.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.addresslocal.adpter.AddressLocalAdapter;
import shop.gaship.gashipfront.addresslocal.dto.request.AddressLocalModifyRequestDto;
import shop.gaship.gashipfront.addresslocal.service.AddressLocalService;

/**
 * 설명작성란
 *
 * @author : 유호철
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class AddressLocalServiceImpl implements AddressLocalService {
    private final AddressLocalAdapter adapter;

    @Override
    public boolean modifyAddressLocal(Integer localNo, boolean isDelivery) {
        return adapter.modifyAddressIsDelivery(new AddressLocalModifyRequestDto(localNo, isDelivery));
    }

}

package shop.gaship.gashipfront.addresslocal.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipfront.addresslocal.dto.response.AddressSubLocalResponseDto;
import shop.gaship.gashipfront.addresslocal.service.AddressLocalService;

/**
 * ajax 를 사용하기위한 rest controller 입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
@RestController
@RequestMapping("/api/addressLocals")
@RequiredArgsConstructor
public class AddressSubLocalController {
    private final AddressLocalService addressLocalService;

    @GetMapping(params = "address")
    public List<AddressSubLocalResponseDto> subCategoryList(
        @RequestParam("address") String address) {
        return addressLocalService.addressSubList(address);
    }

    @PutMapping(value = "/{addressNo}")
    public boolean addressModify(
        @PathVariable("addressNo") Integer addressNo,
        @RequestParam("isDelivery") Boolean isDelivery) {
        return addressLocalService.modifyAddressLocal(addressNo, isDelivery);
    }
}

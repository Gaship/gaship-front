package shop.gaship.gashipfront.addresslocal.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.gaship.gashipfront.addresslocal.dto.response.AddressLocalResponseDto;
import shop.gaship.gashipfront.addresslocal.service.AddressLocalService;

/**
 * 설명작성란
 *
 * @author : 유호철
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
public class AddressLocalController {
    private final AddressLocalService service;

    @GetMapping("/admin/addressLocal")
    public String addressLocalMain(Model model) {
        List<AddressLocalResponseDto> result = service.addressList();
        model.addAttribute("addressList", result);
        return "layout/admin/addressLocal/addressLocalList";
    }

    @PutMapping("/admin/addressLocal/{addressNo}")
    public String addressLocalModify(@PathVariable("addressNo") Integer addressNo,
                                     @RequestParam("isDelivery") boolean isDelivery) {
        service.modifyAddressLocal(addressNo, isDelivery);
        return "redirect:addresslocal/addressLocalMain";
    }
}

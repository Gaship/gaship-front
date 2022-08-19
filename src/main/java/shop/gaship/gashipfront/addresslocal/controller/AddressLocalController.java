package shop.gaship.gashipfront.addresslocal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String addressLocalMain(){

        return "addresslocal/addressLocalMain";
    }

    @GetMapping("/admin/addressLocal/{addressNo}")
    public String addressLocalModify(@PathVariable("addressNo") Integer addressNo,
                                     @RequestParam("isDelivery") boolean isDelivery){
        service.modifyAddressLocal(addressNo, isDelivery);
        return "redirect:addresslocal/addressLocalMain";
    }
}

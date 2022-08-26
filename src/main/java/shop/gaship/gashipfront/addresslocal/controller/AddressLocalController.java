package shop.gaship.gashipfront.addresslocal.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/admin/address-local")
    public String addressLocalMain(Model model) {
        List<AddressLocalResponseDto> result = service.addressList();
        model.addAttribute("addressList", result);
        return "layout/admin/addressLocal/addressLocalList";
    }
}

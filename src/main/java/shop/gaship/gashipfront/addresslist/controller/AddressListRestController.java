package shop.gaship.gashipfront.addresslist.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shop.gaship.gashipfront.addresslist.dto.request.AddressAddRequestDto;
import shop.gaship.gashipfront.addresslist.dto.response.AddressListResponseDto;
import shop.gaship.gashipfront.addresslist.service.AddressListService;
import shop.gaship.gashipfront.security.common.dto.UserDetailsDto;

import java.util.List;

/**
 * 배송지 목록 관련 rest controller 입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/members/address-list")
public class AddressListRestController {

    private final AddressListService addressListService;

    @GetMapping
    public List<AddressListResponseDto> addressLists(
        @AuthenticationPrincipal UserDetailsDto user) {

        return addressListService.findAddressListAll(user.getMemberNo());
    }

    @PostMapping
    public void addressAdd(@AuthenticationPrincipal UserDetailsDto user,
        @RequestBody AddressAddRequestDto addressAddRequestDto) {
        addressListService.addAddress(user.getMemberNo(), addressAddRequestDto);

    }
}

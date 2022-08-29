package shop.gaship.gashipfront.addresslist.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import shop.gaship.gashipfront.addresslist.dto.request.AddressAddRequestDto;
import shop.gaship.gashipfront.addresslist.dto.response.AddressListResponseDto;
import shop.gaship.gashipfront.addresslist.service.AddressListService;
import shop.gaship.gashipfront.security.basic.dto.SignInSuccessUserDetailsDto;
import shop.gaship.gashipfront.security.common.dto.UserDetailsDto;
import shop.gaship.gashipfront.util.dto.PageResponse;

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
    public PageResponse<AddressListResponseDto> addressLists(
            @AuthenticationPrincipal UserDetails user, Pageable pageable) {

        if(user instanceof UserDetailsDto) {
            return addressListService
                    .findAddressLists(Long.valueOf(((UserDetailsDto) user).getMemberNo()),
                            pageable);
        }
        return addressListService
                .findAddressLists(((SignInSuccessUserDetailsDto) user).getMemberNo(),
                        pageable);
    }

    @PostMapping
    public void addressAdd(@AuthenticationPrincipal UserDetails user,
                           @RequestBody AddressAddRequestDto addressAddRequestDto){

        if(user instanceof UserDetailsDto) {
            addressListService
                    .addAddress(((UserDetailsDto) user).getMemberNo(),
                            addressAddRequestDto);
        } else {
            addressListService
                    .addAddress((((SignInSuccessUserDetailsDto)user)
                                    .getMemberNo()).intValue(),
                            addressAddRequestDto);
        }
    }
}

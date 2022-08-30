package shop.gaship.gashipfront.addresslist.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipfront.addresslist.dto.request.AddressAddRequestDto;
import shop.gaship.gashipfront.addresslist.dto.response.AddressListResponseDto;
import shop.gaship.gashipfront.addresslist.service.AddressListService;
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
        @AuthenticationPrincipal UserDetailsDto user, Pageable pageable) {

        return addressListService.findAddressLists(user.getMemberNo(), pageable);
    }

    @PostMapping
    public void addressAdd(@AuthenticationPrincipal UserDetailsDto user,
        @RequestBody AddressAddRequestDto addressAddRequestDto) {
        addressListService.addAddress(user.getMemberNo(), addressAddRequestDto);

    }
}

package shop.gaship.gashipfront.addresslist.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.gaship.gashipfront.addresslist.dto.request.AddressListAddRequestDto;
import shop.gaship.gashipfront.addresslist.dto.request.AddressListModifyRequestDto;
import shop.gaship.gashipfront.addresslist.service.AddressListService;
import shop.gaship.gashipfront.security.common.dto.UserDetailsDto;

import java.util.Objects;

/**
 * 배송지목록의 컨트롤러입니다.
 *
 * @author 최정우
 * @author 김세미
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/member/address-list")
public class AddressListController {
    private static final String DEFAULT_PATH = "addressLists";
    private final AddressListService addressListService;

    /**
     * 배송지목록을 등록하고 배송지목록으로 돌아갑니다.
     *
     * @param request 배송지목록등록에 쓰이는 정보입니다.
     * @return 배송지목록 목록을 보여주는 페이지
     * @author 최정우, 김세미
     */
    @PostMapping
    public String addressListAdd(@ModelAttribute @Valid AddressListAddRequestDto request) {
        addressListService.addAddressList(request);

        return "redirect:/member/address-list";
    }

    /**
     * 배송지목록을 수정하고 배송지목록으로 돌아갑니다.
     *
     * @param request 배송지목록수정에 쓰이는 정보입니다.
     * @return 배송지목록 목록을 보여주는 페이지
     * @author 최정우
     */
    @PutMapping("/{addressListNo}")
    public String addressListModify(@ModelAttribute AddressListModifyRequestDto request,
                                    RedirectAttributes redirectAttributes) {
        addressListService.modifyAddressList(request);
        redirectAttributes.addAttribute("memberNo", request.getMemberNo());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/member/address-lists";
    }

    /**
     * 배송지목록을 삭제(상태변경)하고 배송지목록으로 돌아갑니다.
     *
     * @param addressListNo 수정할 배송지목록의 id 입니다.
     * @return 배송지목록 목록을 보여주는 페이지
     * @author 최정우, 김세미
     */
    @DeleteMapping("/{addressListNo}")
    public String addressListRemove(@AuthenticationPrincipal UserDetailsDto user,
                                    @PathVariable Long addressListNo) {

        addressListService.deleteAddressList(user.getMemberNo(), addressListNo);
        return "redirect:/member/address-list";
    }

//    /**
//     * 배송지목록의 상세 페이지 입니다.
//     *
//     * @param addressListNo 조회하려는 배송지목록의 id 입니다.
//     * @param model 모델
//     * @return 배송지목록 상세페이지
//     * @author 최정우
//     */
//    @GetMapping("/{addressListNo}")
//    public String addressListDetails(@PathVariable Integer memberNo,
//                                     @PathVariable Long addressListNo,
//                                     RedirectAttributes redirectAttributes,
//                                     Model model) {
//        redirectAttributes.addAttribute("memberNo", memberNo);
//        redirectAttributes.addAttribute("status", true);
//        model.addAttribute("response", addressListService.findAddressList(memberNo, addressListNo));
//        return "addressListInfo";
//    }

    /**
     * 배송지 목록입니다.
     *
     * @param user     현재 로그인한 사용자입니다.
     * @param pageable 배송지 목록에 보여줄 정보에 대한 페이지 정보입니다.
     * @return 배송지 목록을 보여주는 페이지 입니다.
     * @author 김세미
     */
    @GetMapping
    public String addressLists(@AuthenticationPrincipal UserDetailsDto user,
                               @PageableDefault(page = 0, size = 10) Pageable pageable,
                               Model model) {
        if(Objects.isNull(user)) {
            return "redirect:/";
        }

        model.addAttribute("addressList",
                addressListService.findAddressLists(user.getMemberNo(), pageable));

        return "addresslist/addressList";
    }
}

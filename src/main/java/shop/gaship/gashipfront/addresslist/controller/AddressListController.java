package shop.gaship.gashipfront.addresslist.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.gaship.gashipfront.addresslist.dto.request.AddressListAddRequestDto;
import shop.gaship.gashipfront.addresslist.dto.request.AddressListModifyRequestDto;
import shop.gaship.gashipfront.addresslist.service.AddressListService;

/**
 * 배송지목록의 컨트롤러입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/members/{memberNo}/address-lists")
public class AddressListController {
    private static final String DEFAULT_PATH = "addressLists";
    private final AddressListService addressListService;

    /**
     * 배송지목록을 등록하고 배송지목록으로 돌아갑니다.
     *
     * @param request 배송지목록등록에 쓰이는 정보입니다.
     * @return 배송지목록 목록을 보여주는 페이지
     * @author 최정우
     */
    @PostMapping
    public String addressListAdd(@ModelAttribute @Valid AddressListAddRequestDto request,
                                 RedirectAttributes redirectAttributes) {
        addressListService.addAddressList(request);
        redirectAttributes.addAttribute("memberNo", request.getMemberNo());

        return "redirect:/members/{memberNo}/address-lists";
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
        return "redirect:/members/{memberNo}/address-lists";
    }

    /**
     * 배송지목록을 삭제(상태변경)하고 배송지목록으로 돌아갑니다.
     *
     * @param addressListNo 수정할 배송지목록의 id 입니다.
     * @return 배송지목록 목록을 보여주는 페이지
     * @author 최정우
     */
    @DeleteMapping("/{addressListNo}")
    public String addressListRemove(@PathVariable Long memberNo,
                                    @PathVariable Long addressListNo,
                                    RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("memberNo", memberNo);
        redirectAttributes.addAttribute("status", true);
        addressListService.deleteAddressList(memberNo, addressListNo);
        return "redirect:/members/{memberNo}/address-lists";
    }

    /**
     * 배송지목록의 상세 페이지 입니다.
     *
     * @param addressListNo 조회하려는 배송지목록의 id 입니다.
     * @param model 모델
     * @return 배송지목록 상세페이지
     * @author 최정우
     */
    @GetMapping("/{addressListNo}")
    public String addressListDetails(@PathVariable Long memberNo,
                                     @PathVariable Long addressListNo,
                                     RedirectAttributes redirectAttributes,
                                     Model model) {
        redirectAttributes.addAttribute("memberNo", memberNo);
        redirectAttributes.addAttribute("status", true);
        model.addAttribute("response", addressListService.findAddressList(memberNo, addressListNo));
        return "addressListInfo";
    }

    /**
     * 배송지목록을 수정하고 배송지목록으로 돌아갑니다.
     *
     * @param memberNo 조회하려는 회원 id 입니다.
     * @param pageable 조회하려는 페이지 번호와 사이즈가 담겨져있는 객체
     * @param model    모델
     * @return 배송지목록 목록을 보여주는 페이지
     * @author 최정우
     */
    @GetMapping
    public String addressLists(@PathVariable Long memberNo,
                               Pageable pageable,
                               RedirectAttributes redirectAttributes,
                               Model model) {
        redirectAttributes.addAttribute("memberNo", memberNo);
        redirectAttributes.addAttribute("status", true);
        model.addAttribute("response", addressListService.findAddressLists(memberNo, pageable));
        return DEFAULT_PATH;
    }
}

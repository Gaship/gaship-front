package shop.gaship.gashipfront.member.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.gaship.gashipfront.member.dto.request.MemberModifyByAdminDto;
import shop.gaship.gashipfront.member.dto.request.MemberModifyRequestDto;
import shop.gaship.gashipfront.member.dto.response.MemberResponseDto;
import shop.gaship.gashipfront.member.service.MemberService;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * 멤버 컨트롤러.
 *
 * @author 최정우
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private static final String RESPONSE = "response";
    private static final String MEM_NO = "response";
    private static final String STATUS = "response";

    /**
     * 멤버의 개인정보를 수정하는 컨트롤러.
     *
     * @param request            수정정보에 대한 정보가 들어있는 dto
     * @param memberNo           회원 id
     * @param redirectAttributes redirectAttributes
     * @return 멤버의 정보를 보여주는 페이지
     * @author 최정우
     */
    @PutMapping("members/{memberNo}")
    public String memberModify(
            @ModelAttribute @Valid MemberModifyRequestDto request,
            @PathVariable Integer memberNo,
            RedirectAttributes redirectAttributes) {
        memberService.modifyMember(request);
        redirectAttributes.addAttribute(MEM_NO, memberNo);
        redirectAttributes.addAttribute(STATUS, true);

        return "redirect:/members/{memberNo}";
    }

    /**
     * 관리자가 회원의 상태를 변경하는 컨트롤러.
     *
     * @param request            수정하려는 상태가 담긴 dto
     * @param memberNo           변경하려는 회원의 id
     * @param redirectAttributes redirectAttributes
     * @return 멤버 전용 회원 개인정보 페이지
     * @author 최정우
     */
    @PutMapping("members/{memberNo}/role")
    public String memberModifyByAdmin(
            @ModelAttribute MemberModifyByAdminDto request,
            @PathVariable Integer memberNo,
            RedirectAttributes redirectAttributes) {
        memberService.modifyMemberByAdmin(request);
        redirectAttributes.addAttribute(MEM_NO, memberNo);
        redirectAttributes.addAttribute(STATUS, true);

        return "redirect:/members/{memberNo}/role";
    }

    /**
     * 회원을 삭제하는 컨트롤러.
     *
     * @param memberNo           삭제하려는 회원의 id
     * @param redirectAttributes redirectAttributes
     * @return 홈페이지.
     * @author 최정우
     */
    @DeleteMapping("members/{memberNo}")
    public String memberRemove(
            @PathVariable Integer memberNo,
            RedirectAttributes redirectAttributes) {
        memberService.removeMember(memberNo);
        redirectAttributes.addAttribute(STATUS, true);

        return "showHome";
    }

    /**
     * 회원이 자신의 상세정보를 조회하는 컨트롤러.
     *
     * @param memberNo 조회하려는 회원 id
     * @param redirectAttributes redirectAttributes
     * @param model model
     * @return 회원정보 상세 페이지
     * @author 최정우
     */
    @GetMapping("members/{memberNo}")
    public String memberDetails(
            @PathVariable Integer memberNo,
            RedirectAttributes redirectAttributes,
            Model model) {
        MemberResponseDto dto = memberService.findMember(memberNo);
        model.addAttribute(RESPONSE, dto);
        redirectAttributes.addAttribute(MEM_NO, memberNo);
        redirectAttributes.addAttribute(STATUS, true);

        return "memberInfo";
    }

    /**
     * 관리자가 회원의 상세정보를 조회하는 컨트롤러.
     *
     * @param memberNo 조회하려는 회원 id
     * @param redirectAttributes redirectAttributes
     * @param model model
     * @return 관리자 전용 회원정보 상세 페이지
     * @author 최정우
     */
    @GetMapping("members/{memberNo}/role")
    public String memberDetailsByAdmin(
            @PathVariable Integer memberNo,
            RedirectAttributes redirectAttributes,
            Model model) {
        MemberResponseDto dto = memberService.findMember(memberNo);
        model.addAttribute(RESPONSE, dto);
        redirectAttributes.addAttribute(MEM_NO, memberNo);
        redirectAttributes.addAttribute(STATUS, true);

        return "memberInfoAdmin";
    }

    /**
     * 회원 목록 상세페이지.
     *
     * @param redirectAttributes redirectAttributes
     * @return 태그 목록과 자신이 선택한 태그를 보여주는 페이지
     * @author 최정우
     */
    @GetMapping("members/role")
    public String memberList(
            RedirectAttributes redirectAttributes,
            Pageable pageable,
            Model model) {
        PageResponse<MemberResponseDto> dto = memberService.findMembers(pageable);
        model.addAttribute(RESPONSE, dto);
        redirectAttributes.addAttribute(STATUS, true);

        return "memberList";
    }
}

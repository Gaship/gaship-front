package shop.gaship.gashipfront.member.controller;

import java.util.Objects;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.gaship.gashipfront.member.dto.request.MemberCreationRequest;
import shop.gaship.gashipfront.member.dto.request.MemberModifyByAdminDto;
import shop.gaship.gashipfront.member.dto.request.MemberModifyRequestDto;
import shop.gaship.gashipfront.member.dto.response.MemberResponseByAdminDto;
import shop.gaship.gashipfront.member.dto.response.MemberResponseDto;
import shop.gaship.gashipfront.member.exception.SignUpDenyException;
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
    private static final int VERIFICATION_COOKIE_MAX_AGE = 180;

    /**
     * 회원가입 페이지를 보여주는 컨트롤러.
     *
     * @return 회원가입 페이지 뷰
     * @author 김민수
     */
    @GetMapping("/members/create")
    public String memberSignup() {
        return "/member/signUpForm";
    }

    /**
     * 회원가입을 실행 요청을 받는 메서드입니다.
     *
     * @param memberCreationRequest 회원가입 양식객체입니다.
     * @return 홈으로 리다이렉션합니다.
     */
    @PostMapping("/members/create")
    public String doSignUp(@Valid MemberCreationRequest memberCreationRequest,
                           @CookieValue(value = "signUp", required = false)
                           Cookie signUpVerifiedCookie) {
        if(Objects.isNull(signUpVerifiedCookie)) {
            String errorMessage = "본인 확인 이메일 인증을 완료 후 회원가입을 진행해주십시오.";
            throw new SignUpDenyException(errorMessage);
        }

        String verifyCode = signUpVerifiedCookie.getValue();
        memberService.checkApprovedEmail(verifyCode);

        memberCreationRequest.setVerifyCode(verifyCode);
        memberService.executeSignUp(memberCreationRequest);

        return "redirect:/";
    }


    /**
     * 멤버의 개인정보를 수정하는 컨트롤러.
     *
     * @param request            수정정보에 대한 정보가 들어있는 dto
     * @param memberNo           회원 id
     * @param redirectAttributes redirectAttributes
     * @return 멤버의 정보를 보여주는 페이지
     * @author 최정우
     */
    @PutMapping("/members/{memberNo}")
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
    @PutMapping("/admin/members/update/{memberNo}")
    public String memberModifyByAdmins(
            @ModelAttribute @Valid MemberModifyByAdminDto request,
            @PathVariable Integer memberNo,
            RedirectAttributes redirectAttributes) {
        memberService.modifyMemberByAdmin(request);
        redirectAttributes.addAttribute(MEM_NO, memberNo);
        redirectAttributes.addAttribute(STATUS, true);

        return "redirect:/members/{memberNo}";
    }

    /**
     * 회원을 삭제하는 컨트롤러.
     *
     * @param memberNo           삭제하려는 회원의 id
     * @param redirectAttributes redirectAttributes
     * @return 홈페이지.
     * @author 최정우
     */
    @DeleteMapping("/members/{memberNo}")
    public String memberRemove(
            @PathVariable Integer memberNo,
            RedirectAttributes redirectAttributes) {
        memberService.removeMember(memberNo);
        redirectAttributes.addAttribute(STATUS, true);

        return "redirect:/";
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
    @GetMapping("/members/{memberNo}")
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
    @GetMapping("/admin/members/{memberNo}")
    public String memberDetailsByAdmin(
            @PathVariable Integer memberNo,
            RedirectAttributes redirectAttributes,
            Model model) {
        MemberResponseByAdminDto dto = memberService.findMemberByAdmin(memberNo);
        model.addAttribute(RESPONSE, dto);
        redirectAttributes.addAttribute(MEM_NO, memberNo);
        redirectAttributes.addAttribute(STATUS, true);

        return "layout/admin/member";
    }

    /**
     * 회원 목록 상세페이지.
     *
     * @return 태그 목록과 자신이 선택한 태그를 보여주는 페이지
     * @author 최정우
     */
    @GetMapping("/admin/members")
    public String memberList(
            Pageable pageable,
            Model model) {
        PageResponse<MemberResponseByAdminDto> dto = memberService.findMembers(pageable);
        model.addAttribute(RESPONSE, dto);

        return "layout/admin/member/memberList";
    }

    @GetMapping("/members/signUp/email-verify/{verifyCode}")
    public String requestApproveComplete(@PathVariable("verifyCode") String verifyCode,
                                         HttpServletRequest request, HttpServletResponse httpResponse) {
        memberService.requestApproveEmailVerification(verifyCode);

        Cookie cookie = new Cookie("signUp", verifyCode);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(VERIFICATION_COOKIE_MAX_AGE);
        cookie.setSecure(true);
        httpResponse.addCookie(cookie);
        return "member/signUpApprove";
    }
}

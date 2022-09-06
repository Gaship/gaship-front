package shop.gaship.gashipfront.member.controller;

import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import shop.gaship.gashipfront.member.dto.request.ReissuePasswordRequest;
import shop.gaship.gashipfront.member.dto.response.MemberResponseByAdminDto;
import shop.gaship.gashipfront.member.dto.response.MemberResponseDto;
import shop.gaship.gashipfront.member.exception.SignUpDenyException;
import shop.gaship.gashipfront.member.service.MemberService;
import shop.gaship.gashipfront.security.common.dto.UserDetailsDto;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * 멤버 컨트롤러.
 *
 * @author 최정우
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;
    private static final String RESPONSE = "response";
    private static final String STATUS = "status";
    private static final int VERIFICATION_COOKIE_MAX_AGE = 180;

    /**
     * 회원가입 페이지를 보여주는 컨트롤러.
     *
     * @return 회원가입 페이지 뷰
     * @author 김민수
     */
    @GetMapping("/members/create")
    public String memberSignup() {
        return "member/signUpForm";
    }

    /**
     * 회원가입을 실행 요청을 받는 메서드입니다.
     *
     * @param memberCreationRequest 회원가입 양식객체입니다.
     * @return 홈으로 리다이렉션합니다.
     */
    @PostMapping("/members/create")
    public String doSignUp(@Valid MemberCreationRequest memberCreationRequest,
                           HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String verifyCode = session.getAttribute("signUp").toString();

        if(Objects.isNull(verifyCode)) {
            String errorMessage = "본인 확인 이메일 인증을 완료 후 회원가입을 진행해주십시오.";
            throw new SignUpDenyException(errorMessage);
        }

        session.removeAttribute("signUp");

        Optional.ofNullable(session.getAttribute("nicknameDuplication"))
            .ifPresentOrElse(
                isNicknameDuplicated -> {
                    Boolean result = (Boolean) isNicknameDuplicated;
                    if(result) {
                        throw new SignUpDenyException("중복된 닉네임입니다. 다른 닉네임을 생각해주세요.");
                    }
                },
                () -> {
                    throw new SignUpDenyException("닉네임 중복확인이 필요합니다.");
            });


        Optional.ofNullable(session.getAttribute("recommendMember"))
            .ifPresent(recommendMemberNo ->
                memberCreationRequest.setRecommendMemberNo((Integer) recommendMemberNo));

        memberService.checkApprovedEmail(verifyCode);

        memberCreationRequest.setVerifyCode(verifyCode);
        memberService.executeSignUp(memberCreationRequest);

        return "redirect:/";
    }


    /**
     * 멤버의 개인정보를 수정하는 컨트롤러.
     *
     * @param request            수정정보에 대한 정보가 들어있는 dto
     * @return 멤버의 정보를 보여주는 페이지
     * @author 최정우
     */
    @PutMapping("/members")
    public String memberModify(@ModelAttribute @Valid MemberModifyRequestDto request,
                               @AuthenticationPrincipal UserDetailsDto userDetails) {
        request.setMemberNo(userDetails.getMemberNo());
        memberService.modifyMember(request);

        return "redirect:/members/details";
    }

    /**
     * 관리자가 회원의 상태를 변경하는 컨트롤러.
     *
     * @param request            수정하려는 상태가 담긴 dto
     * @param memberNo           변경하려는 회원의 id
     * @return 멤버 전용 회원 개인정보 페이지
     * @author 최정우
     */
    @PutMapping("/admin/members/update/{memberNo}")
    public String memberModifyByAdmins(
            @ModelAttribute @Valid MemberModifyByAdminDto request,
            @PathVariable Integer memberNo) {
        memberService.modifyMemberByAdmin(request);

        return "redirect:/members/" + memberNo;
    }

    /**
     * 회원을 삭제하는 컨트롤러.
     *
     * @return 홈페이지.
     * @author 최정우
     */
    @DeleteMapping("/members")
    public String memberRemove(@AuthenticationPrincipal UserDetailsDto userDetailsDto) {
        memberService.removeMember(userDetailsDto.getMemberNo());

        return "redirect:/";
    }

    /**
     * 회원이 자신의 상세정보를 조회하는 컨트롤러.
     *
     * @param model model
     * @return 회원정보 상세 페이지
     * @author 최정우
     */
    @GetMapping("/members/details")
    public String memberDetails(@AuthenticationPrincipal UserDetailsDto userDetailsDto,
                                Model model) {
        Integer memberNo = userDetailsDto.getMemberNo();
        MemberResponseDto dto = memberService.findMember(memberNo);
        model.addAttribute("member", dto);
        model.addAttribute("memberNo", memberNo);
        return "member/memberDetails";
    }

    @GetMapping("/members/exit")
    public String showMemberExitPage(@AuthenticationPrincipal UserDetailsDto userDetails, Model model) {
        model.addAttribute("memberNo", userDetails.getMemberNo());
        return "member/memberExit";
    }

    /**
     * 회원 목록 수정페이지.
     *
     * @return 태그 목록과 자신이 선택한 태그를 보여주는 페이지
     * @author 김민수
     */
    @GetMapping("/members/update")
    public String memberDetailsUpdate(@AuthenticationPrincipal UserDetailsDto userDetailsDto,
                                      Model model) {
        Integer memberNo = userDetailsDto.getMemberNo();
        MemberResponseDto dto = memberService.findMember(memberNo);
        model.addAttribute("member", dto);
        model.addAttribute("memberNo", memberNo);

        return "member/memberDetailModify";
    }

    /**
     * 관리자가 회원의 상세정보를 조회하는 컨트롤러.
     *
     * @param memberNo 조회하려는 회원 id
     * @param model model
     * @return 관리자 전용 회원정보 상세 페이지
     * @author 최정우
     */
    @GetMapping("/admin/members/{memberNo}")
    public String memberDetailsByAdmin(
            @PathVariable Integer memberNo,
            Model model) {
        MemberResponseByAdminDto dto = memberService.findMemberByAdmin(memberNo);
        model.addAttribute(RESPONSE, dto);

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
        Pageable tempPageable = PageRequest.of(pageable.getPageNumber (), 10);
        PageResponse<MemberResponseByAdminDto> dto = memberService.findMembers(tempPageable);

        model.addAttribute("content", dto.getContent());
        model.addAttribute("next", dto.isNext());
        model.addAttribute("previous", dto.isPrevious());
        model.addAttribute("totalPage", dto.getTotalPages());
        model.addAttribute("pageNum", dto.getNumber() + 1);
        model.addAttribute("previousPageNo", dto.getNumber() - 1);
        model.addAttribute("nextPageNo", dto.getNumber() + 1);
        model.addAttribute("size", tempPageable.getPageSize());
        model.addAttribute("uri", "/admin/members");

        return "member/memberList";
    }

    @GetMapping("/members/signUp/email-verify/{verifyCode}")
    public String requestApproveComplete(@PathVariable("verifyCode") String verifyCode,
                                         HttpServletRequest request) {
        log.error("-----------------------------------------------------");
        Iterator<String> headers = request.getHeaderNames().asIterator();
        while (headers.hasNext()){
            log.debug("인증 요청한 헤더 : key: {}, value : {}", headers.next(), request.getHeader(headers.next()));
        }
        log.error("-----------------------------------------------------");
        memberService.requestApproveEmailVerification(verifyCode);

        HttpSession session = request.getSession(false);
        session.setAttribute("signUp", verifyCode);

        return "member/signUpApprove";
    }

    @GetMapping("/members/forgot-password")
    public String showForgotPassword() {

        return "member/forgotPassword";
    }

    @PostMapping("/members/forgot-password")
    public String requestReissuePassword(ReissuePasswordRequest reissuePasswordRequest) {
        memberService.reissuePassword(reissuePasswordRequest);
        return "redirect:/login";
    }
}

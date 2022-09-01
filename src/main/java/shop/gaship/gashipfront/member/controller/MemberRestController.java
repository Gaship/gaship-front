package shop.gaship.gashipfront.member.controller;

import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipfront.member.exception.CanNotRequestEmailVerify;
import shop.gaship.gashipfront.member.service.MemberService;

/**
 * 회원가입 이메일 인증요청을 받는 컨트롤러입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberRestController {
    private final MemberService memberService;

    /**
     * 회원가입 이메일 요청 컨트롤러입니다.
     *
     * @param email 인증요청을 보낼 이메일입니다.
     * @return 성공메세지 객체를 반환합니다.
     */
    @GetMapping("/verify")
    public Map<String, String> requestMemberIdentify(
        @RequestParam(value = "email", required = false) String email) {
        if (Objects.isNull(email) || Objects.equals(email, "")) {
            throw new CanNotRequestEmailVerify();
        }
        memberService.verifySignUpCode(email);

        return Map.of("message", "이메일 발송인증코드를 발송했습니다. 3분 이내에 인증을 완료해주세요.");
    }

    /**
     * 회원을 삭제하는 rest 컨트롤러.
     *
     * @param memberNo           삭제하려는 회원의 id
     * @author 김민수
     */
    @DeleteMapping("/{memberNo}")
    public void memberRemove(@PathVariable Integer memberNo) {
        memberService.removeMember(memberNo);
    }
}

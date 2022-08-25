package shop.gaship.gashipfront.membergrade.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.gaship.gashipfront.membergrade.dto.request.MemberGradeAddRequestDto;
import shop.gaship.gashipfront.membergrade.service.MemberGradeService;
import javax.validation.Valid;


/**
 *
 *
 * @author : 김세미
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/member-grades")
public class MemberGradeController {
    private final MemberGradeService memberGradeService;

    @PostMapping
    public String memberGradeAdd(@Valid MemberGradeAddRequestDto requestDto) {
        memberGradeService.addMemberGrade(requestDto);

        return "redirect:/member-grades";
    }

    @GetMapping
    public String memberGradeList() {

        return "membergrade/membergradeList";
    }

    @GetMapping("/{memberGradeNo}/delete")
    public String memberGradeDelete(@PathVariable Integer memberGradeNo) {
        memberGradeService.deleteMemberGrade(memberGradeNo);

        return "redirect:/member-grades";
    }
}

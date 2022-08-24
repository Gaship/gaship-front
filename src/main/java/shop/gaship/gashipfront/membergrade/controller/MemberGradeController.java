package shop.gaship.gashipfront.membergrade.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shop.gaship.gashipfront.membergrade.dto.request.MemberGradeAddRequestDto;
import shop.gaship.gashipfront.membergrade.dto.response.MemberGradeResponseDto;
import shop.gaship.gashipfront.membergrade.service.MemberGradeService;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 *
 *
 * @author : 김세미
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/member-grades")
public class MemberGradeController {
    private final MemberGradeService memberGradeService;

    @PostMapping
    public String memberGradeAdd(@Valid MemberGradeAddRequestDto requestDto) {
        memberGradeService.addMemberGrade(requestDto);

        return "redirect:/member-grades";
    }

    @GetMapping
    public String memberGradeList() {

        return "/membergrade/membergradeList";
    }

    @GetMapping("/{memberGradeNo}/delete")
    public String memberGradeDelete(@PathVariable Integer memberGradeNo) {
        memberGradeService.deleteMemberGrade(memberGradeNo);

        return "redirect:/member-grades";
    }
}

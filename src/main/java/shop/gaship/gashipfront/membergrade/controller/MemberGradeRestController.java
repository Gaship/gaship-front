package shop.gaship.gashipfront.membergrade.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import shop.gaship.gashipfront.membergrade.dto.response.MemberGradeResponseDto;
import shop.gaship.gashipfront.membergrade.service.MemberGradeService;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 *
 *
 * @author : 김세미
 * @since 1.0
 */
@RestController
@RequestMapping("/front/member-grades")
@RequiredArgsConstructor
public class MemberGradeRestController {
    private final MemberGradeService memberGradeService;

    @GetMapping
    public PageResponse<MemberGradeResponseDto> memberGradeList(Pageable pageable) {
        return memberGradeService.findMemberGrades(pageable);
    }

    @DeleteMapping("/{memberGradeNo}")
    public void memberGradeDelete(@PathVariable Integer memberGradeNo) {
        memberGradeService.deleteMemberGrade(memberGradeNo);
    }
}

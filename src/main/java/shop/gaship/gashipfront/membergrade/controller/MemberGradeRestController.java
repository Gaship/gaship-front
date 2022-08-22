package shop.gaship.gashipfront.membergrade.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}

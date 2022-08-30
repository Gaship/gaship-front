package shop.gaship.gashipfront.membertag.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.gaship.gashipfront.membertag.dto.request.MemberTagRequestDto;
import shop.gaship.gashipfront.membertag.dto.response.MemberTagResponseDto;
import shop.gaship.gashipfront.membertag.service.MemberTagService;
import shop.gaship.gashipfront.security.common.dto.UserDetailsDto;
import shop.gaship.gashipfront.tag.service.TagService;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;


/**
 * 회원태그 관련 요청을 처리합니다.
 *
 * @author 최정우
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/member-tag")
public class MemberTagController {
    private final MemberTagService memberTagService;
    private final TagService tagService;

    /**
     * 자신의 태그를 설정하고 태그목록 리스트화면으로 돌아갑니다.
     *
     * @param request 등록된 태그들의 id 와 회원의 id 가 있습니다.
     * @return 태그 목록을 보여주는 컨트롤러 주소
     * @author 최정우
     */
    @PostMapping
    public String memberTagDeleteAllAndAddAll(
            @AuthenticationPrincipal UserDetailsDto userDetailsDto,
            @RequestBody @Valid MemberTagRequestDto request) {
        if (Objects.isNull(userDetailsDto)) {
            return "redirect:/";
        }
        memberTagService.deleteAllAndAddAllMemberTags(request,userDetailsDto.getMemberNo());

        return "redirect:/member-tag";
    }

    /**
     * 회원이 설정한 태그목록을 보여줍니다.
     *
     * @param model 모델
     * @return 태그 목록과 자신이 선택한 태그를 보여주는 페이지
     * @author 최정우
     */
    @GetMapping
    public String memberTagList(@AuthenticationPrincipal UserDetailsDto userDetailsDto,
                                Model model) {
        if (Objects.isNull(userDetailsDto)) {
            return "redirect:/";
        }
        List<MemberTagResponseDto> memberTags = memberTagService.findMemberTags(userDetailsDto.getMemberNo());
        model.addAttribute("tags", tagService.findTags());
        model.addAttribute("memberTag", memberTags);

        return "membertag/membertagList";
    }
}
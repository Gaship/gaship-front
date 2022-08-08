package shop.gaship.gashipfront.membertag.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.gaship.gashipfront.membertag.dto.request.MemberTagRequestDto;
import shop.gaship.gashipfront.membertag.dto.response.MemberTagResponseDto;
import shop.gaship.gashipfront.membertag.service.MemberTagService;
import shop.gaship.gashipfront.tag.dto.request.TagAddRequestDto;
import shop.gaship.gashipfront.tag.dto.request.TagModifyRequestDto;
import shop.gaship.gashipfront.tag.service.TagService;

import javax.validation.Valid;

/**
 * 회원태그 관련 요청을 처리합니다.
 *
 * @author 최정우
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("members/{memberNo}/tags")
public class MemberTagController {
    private final MemberTagService memberTagService;

    /**
     * 자신의 태그를 설정하고 태그목록 리스트화면으로 돌아갑니다.
     *
     * @param memberNo 조회대상 회원의 id
     * @param request 등록된 태그들의 id 와 회원의 id 가 있습니다.
     * @param redirectAttributes redirectAttributes
     * @return 태그 목록을 보여주는 컨트롤러 주소
     * @author 최정우
     */
    @PostMapping
    public String memberTagDeleteAllAndAddAll(
            @PathVariable Integer memberNo,
            @RequestBody @Valid MemberTagRequestDto request,
            RedirectAttributes redirectAttributes) {
        memberTagService.deleteAllAndAddAllMemberTags(request);
        redirectAttributes.addAttribute("memberNo",memberNo);
        redirectAttributes.addAttribute("status",true);

        return "redirect:/members/{memberNo}/tags";
    }

    /**
     * 회원이 설정한 태그목록을 보여줍니다.
     *
     * @param memberNo 태그목록 조회의 대상이 되는 회원의 id
     * @param model    모델
     * @return 태그 목록과 자신이 선택한 태그를 보여주는 페이지
     * @author 최정우
     */
    @GetMapping
    public String memberTagList(@PathVariable Integer memberNo,
                                Model model) {
        model.addAttribute("response", memberTagService.findMemberTags(memberNo));

        return "basic/memberTag";
    }
}
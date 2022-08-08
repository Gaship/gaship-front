package shop.gaship.gashipfront.tag.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shop.gaship.gashipfront.tag.dto.request.TagAddRequestDto;
import shop.gaship.gashipfront.tag.dto.request.TagModifyRequestDto;
import shop.gaship.gashipfront.tag.service.TagService;

import javax.validation.Valid;

/**
 * 태그 관련 요청을 처리합니다.
 *
 * @author 최정우
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/tags")
public class TagController {
    private static final String DEFAULT_PATH = "/tags";
    private final TagService tagService;

    /**
     * 태그를 등록하고 태그목록으로 돌아갑니다.
     *
     * @param request 태그등록에 쓰이는 정보입니다.
     * @return 태그 목록을 보여주는 페이지
     * @author 최정우
     */
    @PostMapping
    public String tagAdd(@RequestBody @Valid TagAddRequestDto request) {
        tagService.addTag(request);

        return "redirect:" + DEFAULT_PATH;
    }

    /**
     * 태그를 수정하고 태그목록으로 돌아갑니다.
     *
     * @param request 태그수정에 쓰이는 정보입니다.
     * @return 태그 목록을 보여주는 페이지
     * @author 최정우
     */
    @PutMapping("/{tagNo}")
    public String tagModify(@RequestBody @Valid TagModifyRequestDto request) {
        tagService.modifyTag(request);
        return "redirect:" + DEFAULT_PATH;
    }

    /**
     * 태그를 수정하고 태그목록으로 돌아갑니다.
     *
     * @param tagNo 조회하려는 태그의 id 입니다.
     * @return 태그 수정 페이지
     * @author 최정우
     */
    @GetMapping("/{tagNo}")
    public String tagDetails(@PathVariable Long tagNo,
                             Model model) {
        model.addAttribute("response", tagService.findTag(tagNo));

        return "tagInfo";
    }

    /**
     * 태그를 수정하고 태그목록으로 돌아갑니다.
     *
     * @param model 모델
     * @return 태그 목록을 보여주는 페이지
     * @author 최정우
     */
    @GetMapping
    public String tagList(Model model) {
        model.addAttribute("response", tagService.findTags());
        return "tags";
    }
}

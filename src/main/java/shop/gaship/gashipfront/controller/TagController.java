package shop.gaship.gashipfront.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import shop.gaship.gashipfront.dto.*;
import shop.gaship.gashipfront.service.TagService;

/**
 * packageName    : shop.gaship.gashipfront.controller
 * fileName       : TagController
 * author         : choijungwoo
 * date           : 2022/07/15
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/15        choijungwoo       최초 생성
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/admins/{adminId}/tags")
public class TagController {
    private final TagService tagService;

    @PostMapping
    public ModelAndView registerTag(@ModelAttribute TagRegisterRequestDto tagRegisterRequestDto,
                                    @PathVariable Integer adminId,
                                    ModelAndView modelAndView) {
        tagService.register(adminId, tagRegisterRequestDto);
        modelAndView.setViewName("redirect:/admins/" + adminId + "tags");
        return modelAndView;
    }

    @GetMapping("/{tagId}")
    public ModelAndView getTag(@ModelAttribute TagGetRequestDto tagGetRequestDto,
                               @PathVariable Integer adminId,
                               ModelAndView modelAndView) {
        Mono<TagResponseDto> tag = tagService.getTag(adminId, tagGetRequestDto);
        modelAndView.addObject(tag);
        modelAndView.setViewName("redirect:/admins/" + adminId + "tags/" + tagGetRequestDto.getTagId() + "details");
        return modelAndView;
    }

    @GetMapping
    public ModelAndView getTags(@ModelAttribute TagGetRequestDto tagGetRequestDto,
                                @PathVariable Integer adminId,
                                ModelAndView modelAndView,
                                Pageable pageable) {
        Flux<TagResponseDto> tags = tagService.getTags(adminId, tagGetRequestDto, pageable);
        modelAndView.addObject(tags);
        modelAndView.setViewName("redirect:/admins/" + adminId + "tags");
        return modelAndView;
    }

    @PutMapping("/{tagId}")
    public ModelAndView modifyTag(@ModelAttribute TagModifyRequestDto tagModifyRequestDto,
                                  @PathVariable Integer adminId,
                                  ModelAndView modelAndView) {
        tagService.modifyTag(adminId, tagModifyRequestDto);
        modelAndView.setViewName("redirect:/admins/" + adminId + "tags/" + tagModifyRequestDto.getTagId() + "details");
        return modelAndView;
    }

    @DeleteMapping("/{tagId}")
    public ModelAndView deleteTag(@ModelAttribute TagDeleteRequestDto tagDeleteRequestDto,
                                  @PathVariable Integer adminId,
                                  ModelAndView modelAndView) {
        tagService.deleteTag(adminId, tagDeleteRequestDto);
        modelAndView.setViewName("redirect:/admins/" + adminId + "tags");
        return modelAndView;
    }
}

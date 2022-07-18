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

    /**
     * Register tag model and view.
     *
     * @param tagRegisterRequestDto the tag register request dto
     * @param adminId               the admin id
     * @param modelAndView          the model and view
     * @return the model and view
     */
    @PostMapping
    public ModelAndView tagAdd(@ModelAttribute TagRegisterRequestDto tagRegisterRequestDto,
                                    @PathVariable Integer adminId,
                                    ModelAndView modelAndView) {
        tagService.addTag(adminId, tagRegisterRequestDto);
        modelAndView.setViewName("redirect:/admins/" + adminId + "/tags");
        return modelAndView;
    }


    /**
     * Modify tag model and view.
     *
     * @param tagModifyRequestDto the tag modify request dto
     * @param adminId             the admin id
     * @param modelAndView        the model and view
     * @return the model and view
     */
    @PutMapping("/{tagId}")
    public ModelAndView tagModify(@ModelAttribute TagModifyRequestDto tagModifyRequestDto,
                                  @PathVariable Integer adminId,
                                  ModelAndView modelAndView) {
        tagService.modifyTag(adminId, tagModifyRequestDto);
        modelAndView.setViewName("redirect:/admins/" + adminId + "/tags/" + tagModifyRequestDto.getTagId() + "details");
        return modelAndView;
    }

    /**
     * Delete tag model and view.
     *
     * @param tagDeleteRequestDto the tag delete request dto
     * @param adminId             the admin id
     * @param modelAndView        the model and view
     * @return the model and view
     */
    @DeleteMapping("/{tagId}")
    public ModelAndView tagRemove(@ModelAttribute TagDeleteRequestDto tagDeleteRequestDto,
                                  @PathVariable Integer adminId,
                                  ModelAndView modelAndView) {
        tagService.removeTag(adminId, tagDeleteRequestDto);
        modelAndView.setViewName("redirect:/admins/" + adminId + "/tags");
        return modelAndView;
    }
    /**
     * Gets tag.
     *
     * @param tagGetRequestDto the tag get request dto
     * @param adminId          the admin id
     * @param modelAndView     the model and view
     * @return the tag
     */
    @GetMapping("/{tagId}")
    public ModelAndView tagDetails(@ModelAttribute TagGetRequestDto tagGetRequestDto,
                               @PathVariable Integer adminId,
                               ModelAndView modelAndView) {
        Mono<TagResponseDto> tag = tagService.findTag(adminId, tagGetRequestDto);
        modelAndView.addObject(tag);
        modelAndView.setViewName("tagInfo");
        return modelAndView;
    }

    /**
     * Gets tags.
     *
     * @param tagGetRequestDto the tag get request dto
     * @param adminId          the admin id
     * @param modelAndView     the model and view
     * @param pageable         the pageable
     * @return the tags
     */
    @GetMapping
    public ModelAndView tagList(@ModelAttribute TagGetRequestDto tagGetRequestDto,
                                @PathVariable Integer adminId,
                                ModelAndView modelAndView,
                                Pageable pageable) {
        Flux<TagResponseDto> tags = tagService.findTags(adminId, tagGetRequestDto, pageable);
        modelAndView.addObject(tags);
        modelAndView.setViewName("redirect:/admins/" + adminId + "/tags");
        return modelAndView;
    }
}
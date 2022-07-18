package shop.gaship.gashipfront.tag.service;

import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import shop.gaship.gashipfront.dto.*;
import shop.gaship.gashipfront.tag.dto.*;

/**
 * packageName    : shop.gaship.gashipfront.service
 * fileName       : TagService
 * author         : choijungwoo
 * date           : 2022/07/15
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/15        choijungwoo       최초 생성
 */
public interface TagService {
    /**
     * Add tag.
     *
     * @param adminId               the admin id
     * @param tagRegisterRequestDto the tag register request dto
     */
    void addTag(Integer adminId, TagRegisterRequestDto tagRegisterRequestDto);

    /**
     * Modify tag.
     *
     * @param adminId             the admin id
     * @param tagModifyRequestDto the tag modify request dto
     */
    void modifyTag(Integer adminId, TagModifyRequestDto tagModifyRequestDto);

    /**
     * Remove tag.
     *
     * @param adminId             the admin id
     * @param tagDeleteRequestDto the tag delete request dto
     */
    void removeTag(Integer adminId, TagDeleteRequestDto tagDeleteRequestDto);

    /**
     * Find tag mono.
     *
     * @param adminId          the admin id
     * @param tagGetRequestDto the tag get request dto
     * @return the mono
     */
    Mono<TagResponseDto> findTag(Integer adminId, TagGetRequestDto tagGetRequestDto);

    /**
     * Find tags flux.
     *
     * @param adminId          the admin id
     * @param tagGetRequestDto the tag get request dto
     * @param pageable         the pageable
     * @return the flux
     */
    Flux<TagResponseDto> findTags(Integer adminId, TagGetRequestDto tagGetRequestDto, Pageable pageable);
}

package shop.gaship.gashipfront.tag.adapter;

import shop.gaship.gashipfront.tag.dto.request.TagAddRequestDto;
import shop.gaship.gashipfront.tag.dto.request.TagModifyRequestDto;
import shop.gaship.gashipfront.tag.dto.response.TagResponseDto;

import java.util.List;


/**
 * 태그 adapter 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
public interface TagAdapter {
    /**
     * 태그를 등록하는 어뎁터입니다.
     *
     * @param request 등록하려는 태그의 정보가 들어있습니다.
     * @author 최정우
     */
    void addTag(TagAddRequestDto request);

    /**
     * 태그를 수정하는 어뎁터입니다.
     *
     * @param request 수정하려는 태그의 정보가 들어있습니다.
     * @author 최정우
     */
    void modifyTag(TagModifyRequestDto request);

    /**
     * 태그를 조회하는 어뎁터입니다.
     *
     * @param tagNo 조회하려는 태그의 no 값입니다.
     * @return 하나의 태그의 정보를 반환합니다.
     * @author 최정우
     */
    TagResponseDto findTag(Long tagNo);

    /**
     * 태그목록을 조회하는 어뎁터입니다.
     *
     * @return 태그페이지 정보를 반환합니다.
     * @author 최정우
     */
    List<TagResponseDto> findTags();
}

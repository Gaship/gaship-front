package shop.gaship.gashipfront.tag.service;

import shop.gaship.gashipfront.tag.dto.request.TagAddRequestDto;
import shop.gaship.gashipfront.tag.dto.request.TagModifyRequestDto;
import shop.gaship.gashipfront.tag.dto.response.TagResponseDto;

import java.util.List;

/**
 * 태그 관련 비즈니스 로직 처리 인터페이스입니다.
 *
 * @author 최정우
 * @since 1.0
 */
public interface TagService {
    /**
     * 태그를 등록할 때 실행됩니다.
     *
     * @param request 등록하려는 태그 정보
     * @author 최정우
     */
    void addTag(TagAddRequestDto request);

    /**
     * 태그를 수정할 때 실행됩니다.
     *
     * @param request 수정하려는 태그 정보
     * @author 최정우
     */
    void modifyTag(TagModifyRequestDto request);

    /**
     * 태그를 수정할 때 정보를 불러오기 위해 실행됩니다.
     *
     * @param tagNo 등록하려는 태그 정보
     * @author 최정우
     */
    TagResponseDto findTag(Long tagNo);

    /**
     * 태그를 조회할 때 사용됩니다.
     *
     * @author 최정우
     */
    List<TagResponseDto> findTags();
}

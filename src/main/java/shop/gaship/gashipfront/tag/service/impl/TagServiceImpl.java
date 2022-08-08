package shop.gaship.gashipfront.tag.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.tag.adapter.TagAdapter;
import shop.gaship.gashipfront.tag.dto.request.TagAddRequestDto;
import shop.gaship.gashipfront.tag.dto.request.TagModifyRequestDto;
import shop.gaship.gashipfront.tag.dto.response.TagResponseDto;
import shop.gaship.gashipfront.tag.service.TagService;

import java.util.List;

/**
 * 태그 관련 비즈니스 로직 구현 클래스입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagAdapter tagAdapter;

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTag(TagAddRequestDto request) {
        tagAdapter.addTag(request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void modifyTag(TagModifyRequestDto request) {
        tagAdapter.modifyTag(request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TagResponseDto findTag(Long tagNo) {
        return tagAdapter.findTag(tagNo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TagResponseDto> findTags() {
        return tagAdapter.findTags();
    }
}

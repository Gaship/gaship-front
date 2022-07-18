package shop.gaship.gashipfront.tag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import shop.gaship.gashipfront.dto.*;
import shop.gaship.gashipfront.tag.adapter.TagAdapter;
import shop.gaship.gashipfront.tag.dto.*;

/**
 * packageName    : shop.gaship.gashipfront.service
 * fileName       : TagServiceImpl
 * author         : choijungwoo
 * date           : 2022/07/15
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/15        choijungwoo       최초 생성
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagAdapter tagAdapter;

    @Override
    public void addTag(Integer adminId, TagRegisterRequestDto tagRegisterRequestDto) {
        tagAdapter.addTag(adminId, tagRegisterRequestDto);
    }

    @Override
    public void modifyTag(Integer adminId, TagModifyRequestDto tagModifyRequestDto) {
        tagAdapter.modifyTag(adminId, tagModifyRequestDto);
    }

    @Override
    public void removeTag(Integer adminId, TagDeleteRequestDto tagDeleteRequestDto) {
        tagAdapter.removeTag(adminId, tagDeleteRequestDto);
    }

    @Override
    public Mono<TagResponseDto> findTag(Integer adminId, TagGetRequestDto tagGetRequestDto) {
        return tagAdapter.findTag(adminId, tagGetRequestDto);
    }

    @Override
    public Flux<TagResponseDto> findTags(Integer adminId, TagGetRequestDto tagGetRequestDto, Pageable pageable) {
        return tagAdapter.findTags(adminId, tagGetRequestDto, pageable);
    }

}

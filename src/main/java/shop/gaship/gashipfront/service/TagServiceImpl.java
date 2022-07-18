package shop.gaship.gashipfront.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import shop.gaship.gashipfront.dto.*;
import shop.gaship.gashipfront.repository.TagRepository;

import java.util.List;

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
    private final TagRepository tagRepository;

    @Override
    public void addTag(Integer adminId, TagRegisterRequestDto tagRegisterRequestDto) {
        tagRepository.register(adminId, tagRegisterRequestDto);
    }

    @Override
    public void modifyTag(Integer adminId, TagModifyRequestDto tagModifyRequestDto) {
        tagRepository.modifyTag(adminId, tagModifyRequestDto);
    }

    @Override
    public void removeTag(Integer adminId, TagDeleteRequestDto tagDeleteRequestDto) {
        tagRepository.deleteTag(adminId, tagDeleteRequestDto);
    }

    @Override
    public Mono<TagResponseDto> findTag(Integer adminId, TagGetRequestDto tagGetRequestDto) {
        return tagRepository.getTag(adminId, tagGetRequestDto);
    }

    @Override
    public Flux<TagResponseDto> findTags(Integer adminId, TagGetRequestDto tagGetRequestDto, Pageable pageable) {
        return tagRepository.getTags(adminId, tagGetRequestDto, pageable);
    }

}

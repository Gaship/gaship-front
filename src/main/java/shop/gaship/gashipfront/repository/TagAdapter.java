package shop.gaship.gashipfront.repository;

import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import shop.gaship.gashipfront.dto.*;

/**
 * packageName    : shop.gaship.gashipfront.repository
 * fileName       : TagRepository
 * author         : choijungwoo
 * date           : 2022/07/15
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/15        choijungwoo       최초 생성
 */
public interface TagAdapter {
    Mono<Void> addTag(Integer adminId, TagRegisterRequestDto tagRegisterRequestDto);
    void modifyTag(Integer adminId, TagModifyRequestDto tagModifyRequestDto);
    void removeTag(Integer adminId, TagDeleteRequestDto tagDeleteRequestDto);
    Mono<TagResponseDto> findTag(Integer adminId, TagGetRequestDto tagGetRequestDto);
    Flux<TagResponseDto> findTags(Integer adminId, TagGetRequestDto tagGetRequestDto, Pageable pageable);
}

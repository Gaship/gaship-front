package shop.gaship.gashipfront.service;

import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import shop.gaship.gashipfront.dto.*;

import java.util.List;

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
    void addTag(Integer adminId, TagRegisterRequestDto tagRegisterRequestDto);
    void modifyTag(Integer adminId, TagModifyRequestDto tagModifyRequestDto);
    void removeTag(Integer adminId, TagDeleteRequestDto tagDeleteRequestDto);
    Mono<TagResponseDto> findTag(Integer adminId, TagGetRequestDto tagGetRequestDto);
    Flux<TagResponseDto> findTags(Integer adminId, TagGetRequestDto tagGetRequestDto, Pageable pageable);
}

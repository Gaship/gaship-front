package shop.gaship.gashipfront.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import shop.gaship.gashipfront.dto.*;

import java.util.List;

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
public interface TagRepository {
    void register(Integer adminId, TagRegisterRequestDto tagRegisterRequestDto);
    Mono<TagResponseDto> getTag(Integer adminId, TagGetRequestDto tagGetRequestDto);
    Flux<TagResponseDto> getTags(Integer adminId, TagGetRequestDto tagGetRequestDto, Pageable pageable);
    void modifyTag(Integer adminId, TagModifyRequestDto tagModifyRequestDto);
    void deleteTag(Integer adminId, TagDeleteRequestDto tagDeleteRequestDto);
}

package shop.gaship.gashipfront.membertag.service;

import java.util.List;
import shop.gaship.gashipfront.membertag.dto.request.MemberTagRequestDto;
import shop.gaship.gashipfront.membertag.dto.response.MemberTagCoreResponseDto;


/**
 * 회원 태그 서비스 인터페이스.
 *
 * @author 최정우
 * @since 1.0
 */
public interface MemberTagService {
    /**
     * 기존에 회원이설정한 태그들을 전부 삭제하고 새로운 태그를 등록하는 메서드.
     *
     * @param request 회원 id 와 등록하려는 태그의 id 리스트가 있는 dto
     */
    void deleteAllAndAddAllMemberTags(MemberTagRequestDto request);

    /**
     * 회원이 설정한 태그들을 조회하는 메서드.
     *
     * @param memberNo 조회 대상 id
     * @return 회원이 설정한 태그 리스트
     */
    List<MemberTagCoreResponseDto> findMemberTags(Integer memberNo);

}

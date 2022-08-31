package shop.gaship.gashipfront.membertag.adapter;

import java.util.List;
import shop.gaship.gashipfront.membertag.dto.request.MemberTagRequestDto;
import shop.gaship.gashipfront.membertag.dto.response.MemberTagResponseDto;


/**
 * 회원 태그 어뎁터 인터페이스.
 *
 * @author 최정우
 * @since 1.0
 */
public interface MemberTagAdapter {
    /**
     * 회원이 설정한 모든 태그를 삭제하고 회원이 설정하길 원하는 태그를 등록하는 메서드.
     *
     * @param request 회원 id, 태그 id 리스트 가 담겨있는 객체
     */
    void deleteAllAndAddAllMemberTags(MemberTagRequestDto request,Integer memberNo);

    /**
     * 회원이 설정한 모든 태그를 가져오는 메서드.
     *
     * @param memberNo 조회하고자 하는 대상의 id
     */
    List<MemberTagResponseDto> findMemberTags(Integer memberNo);
}

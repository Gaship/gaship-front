package shop.gaship.gashipfront.membergrade.adapter;

import org.springframework.data.domain.Pageable;
import shop.gaship.gashipfront.membergrade.dto.request.MemberGradeAddRequestDto;
import shop.gaship.gashipfront.membergrade.dto.request.MemberGradeModifyRequestDto;
import shop.gaship.gashipfront.membergrade.dto.response.MemberGradeResponseDto;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * 회원등급에 대한 요청을 보내는 adapter interface 입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
public interface MemberGradeAdapter {
    void addMemberGrade(MemberGradeAddRequestDto requestDto);
    void modifyMemberGrade(Integer memberGradeNo, MemberGradeModifyRequestDto requestDto);
    void deleteMemberGrade(Integer memberGradeNo);
    MemberGradeResponseDto findMemberGrade(Integer memberGradeNo);
    PageResponse<MemberGradeResponseDto> findMemberGradeList(Pageable pageable);
}

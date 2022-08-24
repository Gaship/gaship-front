package shop.gaship.gashipfront.membergrade.service;


import org.springframework.data.domain.Pageable;
import shop.gaship.gashipfront.membergrade.dto.request.MemberGradeAddRequestDto;
import shop.gaship.gashipfront.membergrade.dto.request.MemberGradeModifyRequestDto;
import shop.gaship.gashipfront.membergrade.dto.response.MemberGradeResponseDto;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * 설명작성
 *
 * @author : 김세미
 * @since 1.0
 */
public interface MemberGradeService {

    void addMemberGrade(MemberGradeAddRequestDto requestDto);

    PageResponse<MemberGradeResponseDto> findMemberGrades(Pageable pageable);

    void deleteMemberGrade(Integer memberGradeNo);

    void updateMemberGrade(Integer memberGradeNo, MemberGradeModifyRequestDto requestDto);
}

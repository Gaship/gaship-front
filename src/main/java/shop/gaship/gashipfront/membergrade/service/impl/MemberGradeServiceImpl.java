package shop.gaship.gashipfront.membergrade.service.impl;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.membergrade.adapter.MemberGradeAdapter;
import shop.gaship.gashipfront.membergrade.dto.request.MemberGradeAddRequestDto;
import shop.gaship.gashipfront.membergrade.dto.request.MemberGradeModifyRequestDto;
import shop.gaship.gashipfront.membergrade.dto.response.MemberGradeResponseDto;
import shop.gaship.gashipfront.membergrade.service.MemberGradeService;
import shop.gaship.gashipfront.util.dto.PageResponse;


/**
 *
 *
 * @author : 김세미
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class MemberGradeServiceImpl implements MemberGradeService {
    private final MemberGradeAdapter memberGradeAdapter;

    @Override
    public void addMemberGrade(MemberGradeAddRequestDto requestDto) {
        if (Objects.isNull(requestDto.getIsDefault())) {
            requestDto.setIsDefault(false);
        }

        memberGradeAdapter.addMemberGrade(requestDto);
    }

    @Override
    public PageResponse<MemberGradeResponseDto> findMemberGrades(Pageable pageable) {
        return memberGradeAdapter.findMemberGradeList(pageable);
    }

    @Override
    public void deleteMemberGrade(Integer memberGradeNo) {
        memberGradeAdapter.deleteMemberGrade(memberGradeNo);
    }

    @Override
    public void updateMemberGrade(Integer memberGradeNo,
                                  MemberGradeModifyRequestDto requestDto) {
        memberGradeAdapter.modifyMemberGrade(memberGradeNo, requestDto);
    }
}

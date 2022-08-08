package shop.gaship.gashipfront.membertag.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.member.adapter.MemberAdapter;
import shop.gaship.gashipfront.membertag.adapter.MemberTagAdapter;
import shop.gaship.gashipfront.membertag.dto.request.MemberTagRequestDto;
import shop.gaship.gashipfront.membertag.dto.response.MemberTagResponseDto;
import shop.gaship.gashipfront.membertag.service.MemberTagService;
import shop.gaship.gashipfront.tag.adapter.TagAdapter;
import shop.gaship.gashipfront.tag.dto.response.TagResponseDto;

import java.util.List;

/**
 * 회원태그 서비스 구현체.
 *
 * @author 최정우
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class MemberTagServiceImpl implements MemberTagService {
    private final MemberTagAdapter memberTagAdapter;
    private final TagAdapter tagAdapter;

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteAllAndAddAllMemberTags(MemberTagRequestDto request) {
        memberTagAdapter.deleteAllAndAddAllMemberTags(request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MemberTagResponseDto findMemberTags(Integer memberNo) {
        List<MemberTagResponseDto> dtoList = memberTagAdapter.findMemberTags(memberNo);
        List<TagResponseDto> tags = tagAdapter.findTags();
        return null;
    }
}

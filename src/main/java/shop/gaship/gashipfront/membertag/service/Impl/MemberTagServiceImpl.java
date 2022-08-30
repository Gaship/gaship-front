package shop.gaship.gashipfront.membertag.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.membertag.adapter.MemberTagAdapter;
import shop.gaship.gashipfront.membertag.dto.request.MemberTagRequestDto;
import shop.gaship.gashipfront.membertag.dto.response.MemberTagResponseDto;
import shop.gaship.gashipfront.membertag.dto.response.MemberTagResponseDto;
import shop.gaship.gashipfront.membertag.service.MemberTagService;
import shop.gaship.gashipfront.tag.adapter.TagAdapter;
import shop.gaship.gashipfront.tag.dto.response.TagResponseDto;


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
    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteAllAndAddAllMemberTags(MemberTagRequestDto request, Integer memberNo) {
        memberTagAdapter.deleteAllAndAddAllMemberTags(request,memberNo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MemberTagResponseDto> findMemberTags(Integer memberNo) {
        return memberTagAdapter.findMemberTags(memberNo);
    }
}

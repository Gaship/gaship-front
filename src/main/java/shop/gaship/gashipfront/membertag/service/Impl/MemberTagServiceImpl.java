package shop.gaship.gashipfront.membertag.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.membertag.adapter.MemberTagAdapter;
import shop.gaship.gashipfront.membertag.dto.request.MemberTagRequestDto;
import shop.gaship.gashipfront.membertag.dto.response.MemberTagCoreResponseDto;
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
    public List<MemberTagCoreResponseDto> findMemberTags(Integer memberNo) {
        List<MemberTagResponseDto> dtoList = memberTagAdapter.findMemberTags(memberNo);
        List<TagResponseDto> tags = tagAdapter.findTags();
        List<Integer> tagNoList = dtoList.stream()
                .map(MemberTagResponseDto::getTagNo)
                .collect(Collectors.toList());
        List<MemberTagCoreResponseDto> responseDtoList = new ArrayList<>();
        tags.forEach(ele ->
                responseDtoList.add(MemberTagCoreResponseDto.builder()
                        .tagNo(ele.getTagNo())
                        .title(ele.getTitle())
                        .selected(false)
                        .build()));
        for (Integer targetId : tagNoList) {
            responseDtoList.forEach(dto -> {
                if (Objects.equals(dto.getTagNo(), targetId)) {
                    dto.setSelectedTrue();
                }
            });
        }
        return responseDtoList;
    }
}

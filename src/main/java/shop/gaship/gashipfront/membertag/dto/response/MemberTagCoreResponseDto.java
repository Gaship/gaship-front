package shop.gaship.gashipfront.membertag.dto.response;

import lombok.Builder;
import lombok.Getter;

/**
 * 화면에 반환하기 위한 dto.
 *
 * @author 최정우
 * @since 1.0
 */
@Getter
public class MemberTagCoreResponseDto {
    private final Integer tagNo;
    private final String title;
    private boolean selected;

    /**
     * 모든 태그 리스트와 회원이 선택한 태그들을 반환하는 dto를 만들기 위한 Builder.
     *
     * @param tagNo 태그 id
     * @param title 태그명
     * @param selected 회원이 태그를 선택한 여부
     */
    @Builder
    public MemberTagCoreResponseDto(Integer tagNo, String title, boolean selected) {
        this.tagNo = tagNo;
        this.title = title;
        this.selected = selected;
    }

    public void setSelectedTrue() {
        this.selected = true;
    }
}

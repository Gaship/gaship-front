package shop.gaship.gashipfront.membertag.dto.response;

import lombok.Builder;
import lombok.Getter;

/**
 * @author 최정우
 * @since 1.0
 */
@Getter
public class MemberTagCoreResponseDto {
    private final Integer tagNo;
    private final String title;
    private boolean selected;

    @Builder
    public MemberTagCoreResponseDto(Integer tagNo, String title, boolean selected) {
        this.tagNo = tagNo;
        this.title = title;
        this.selected = selected;
    }

    public void setSelectedTrue(){
        this.selected = true;
    }
}

package shop.gaship.gashipfront.tag.dummy;

import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipfront.tag.dto.request.TagAddRequestDto;
import shop.gaship.gashipfront.tag.dto.request.TagModifyRequestDto;

/**
 * @author 최정우
 * @since 1.0
 */
public class TagDummy {
    public static TagAddRequestDto TagAddRequestDtoDummy(String title) {
        TagAddRequestDto dto = new TagAddRequestDto();
        ReflectionTestUtils.setField(dto, "title", title);
        return dto;
    }

    public static TagModifyRequestDto TagModifyRequestDtoDummy(Integer tagNo, String title) {
        TagModifyRequestDto dto = new TagModifyRequestDto();
        ReflectionTestUtils.setField(dto, "tagNo", tagNo);
        ReflectionTestUtils.setField(dto, "title", title);
        return dto;
    }
}

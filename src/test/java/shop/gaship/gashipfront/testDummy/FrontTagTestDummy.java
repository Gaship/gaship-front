package shop.gaship.gashipfront.testDummy;

import shop.gaship.gashipfront.dto.TagDeleteRequestDto;
import shop.gaship.gashipfront.dto.TagModifyRequestDto;
import shop.gaship.gashipfront.dto.TagRegisterRequestDto;
import shop.gaship.gashipfront.dto.TagResponseDto;

/**
 * packageName    : shop.gaship.gashipfront.testDummy
 * fileName       : FrontTagTestDummy
 * author         : choijungwoo
 * date           : 2022/07/15
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/15        choijungwoo       최초 생성
 */
public class FrontTagTestDummy {
    public static final String title = "테스트 타이틀";

    public static TagRegisterRequestDto CreateTestTagRegisterRequestDto() {
        TagRegisterRequestDto tagRegisterRequestDto = TagRegisterRequestDto.builder().title(title).build();

        return tagRegisterRequestDto;
    }

    public static TagModifyRequestDto CreateTestTagModifyRequestDto() {
        TagModifyRequestDto tagModifyRequestDto = TagModifyRequestDto.builder().tagId(0).title(title).build();

        return tagModifyRequestDto;
    }

    public static TagDeleteRequestDto CreateTestTagDeleteRequestDto() {
        TagDeleteRequestDto tagDeleteRequestDto = TagDeleteRequestDto.builder().tagId(0).title(title).build();

        return tagDeleteRequestDto;
    }

    public static TagResponseDto CreateTestTagResponseDto(String title) {
        TagResponseDto tagResponseDto = TagResponseDto.builder().build();
        return null;//todo
    }

//    public static Tag CreateTestTagEntity() {
//        return Tag.builder().tagNo(1).title("title....1").build();
//    }
//
//    public static List<Tag> CreateTestTagEntityList() {
//        List<Tag> list = new ArrayList<>();
//        IntStream.rangeClosed(1, 100).forEach(i -> {
//            Tag tag = Tag.builder().tagNo(i).title("title....." + i).build();
//            list.add(tag);
//        });
//        return list;
//    }
}

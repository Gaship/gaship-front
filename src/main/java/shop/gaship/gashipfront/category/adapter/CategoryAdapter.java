package shop.gaship.gashipfront.category.adapter;

import java.util.List;
import shop.gaship.gashipfront.category.dto.request.CategoryCreateRequestDto;
import shop.gaship.gashipfront.category.dto.request.CategoryModifyRequestDto;
import shop.gaship.gashipfront.category.dto.response.CategoryResponseDto;

/**
 * 카테고리 어댑터 인터페이스입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
public interface CategoryAdapter {
    /**
     * 어댑터 카테고리 추가 메서드입니다.
     *
     * @param createRequest 카테고리 생성 요청
     */
    void categoryAdd(CategoryCreateRequestDto createRequest);

    /**
     * 어댑터 카테고리 수정 메서드입니다.
     *
     * @param modifyRequest 카테고리 수정 요청
     */
    void categoryModify(CategoryModifyRequestDto modifyRequest);

    /**
     * 어댑터 카테고리 단건 조회 메서드입니다.
     *
     * @param categoryNo 조회할 카테고리 번호
     * @return 카테고리 응답 객체
     */
    CategoryResponseDto categoryDetails(Integer categoryNo);

    /**
     * 어댑터 카테고리 전체 조회 메서드입니다.
     *
     * @return 전체 카테고리 응답 객체 리스트
     */
    List<CategoryResponseDto> categoryList();

    /**
     * 어댑터 하위 카테고리 전체 조회 메서드입니다.
     *
     * @param categoryNo 하위 카테고리를 조회할 카테고리 번호
     * @return 하위 카테고리 응답 객체 리스트
     */
    List<CategoryResponseDto> lowerCategoryList(Integer categoryNo);

    /**
     * 카테고리 삭제 메서드입니다.
     *
     * @param categoryNo 삭제할 카테고리 번호
     */
    void categoryRemove(Integer categoryNo);
}

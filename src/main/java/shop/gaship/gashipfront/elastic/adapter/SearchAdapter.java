package shop.gaship.gashipfront.elastic.adapter;

import java.util.List;
import shop.gaship.gashipfront.elastic.dto.response.SearchResponseDto;

/**
 * 설명작성란
 *
 * @author : 유호철
 * @since 1.0
 */
public interface SearchAdapter {
    /**
     * 상품이름으로 조회하기위한 메서드입니다.
     *
     * @param name 상품이름이 기입됩니다.
     * @return 검색된 상품들의 간략한 정보가 반환됩니다.
     */
    List<SearchResponseDto> searchName(String name);

    /**
     * 상품코드로 상품들을 조회하기위한 메서드입니다.
     *
     * @param code 상품코드들이 기입됩니다.
     * @return 검색돤 상품들에 대한 간략한 정보가 반환됩니다.
     */
    List<SearchResponseDto> searchCode(String code);
}

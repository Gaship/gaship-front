package shop.gaship.gashipfront.elastic.service;

import java.util.List;
import shop.gaship.gashipfront.elastic.dto.response.SearchResponseDto;

/**
 * 상품의 검색을 위한 서비스입니다.
 *
 * @author : 유호철, 김민수
 * @since 1.0
 */
public interface SearchService {
    List<SearchResponseDto> searchProductKeyword(String productKeyword);
}

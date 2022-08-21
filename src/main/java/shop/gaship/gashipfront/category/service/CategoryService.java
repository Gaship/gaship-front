package shop.gaship.gashipfront.category.service;

import java.util.List;
import shop.gaship.gashipfront.category.dto.response.CategoryResponseDto;

/**
 * 카테고리 서비스 인터페이스입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
public interface CategoryService {
    List<CategoryResponseDto> findCategories();
}

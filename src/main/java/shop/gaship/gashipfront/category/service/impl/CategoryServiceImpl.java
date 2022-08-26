package shop.gaship.gashipfront.category.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.category.adapter.CategoryAdapter;
import shop.gaship.gashipfront.category.dto.request.CategoryCreateRequestDto;
import shop.gaship.gashipfront.category.dto.response.CategoryResponseDto;
import shop.gaship.gashipfront.category.service.CategoryService;

/**
 * 카테고리 서비스 구현체입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryAdapter categoryAdapter;

    @Override
    public List<CategoryResponseDto> findCategories() {
        return categoryAdapter.categoryList();
    }

    @Override
    public void addCategory(CategoryCreateRequestDto createRequest) {
        categoryAdapter.categoryAdd(createRequest);
    }
}

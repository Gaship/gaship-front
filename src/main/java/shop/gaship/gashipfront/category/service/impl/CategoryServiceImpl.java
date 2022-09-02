package shop.gaship.gashipfront.category.service.impl;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.category.adapter.CategoryAdapter;
import shop.gaship.gashipfront.category.dto.request.CategoryCreateRequestDto;
import shop.gaship.gashipfront.category.dto.request.CategoryModifyRequestDto;
import shop.gaship.gashipfront.category.dto.response.CategoryResponseDto;
import shop.gaship.gashipfront.category.service.CategoryService;
import shop.gaship.gashipfront.config.LocalCacheConfig;

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
    public CategoryResponseDto findCategory(Integer categoryNo) {
        return categoryAdapter.categoryDetails(categoryNo);
    }

    @Override
    @Cacheable(LocalCacheConfig.CATEGORY_CACHE)
    public List<CategoryResponseDto> findCategories() {
        return categoryAdapter.categoryList();
    }

    @Override
    public List<CategoryResponseDto> findFlattenCategories() {
        List<CategoryResponseDto> categories = findCategories();
        List<CategoryResponseDto> flattenCategories = new ArrayList<>();
        flatCategories(flattenCategories, categories);

        return flattenCategories;
    }

    @Override
    public void addCategory(CategoryCreateRequestDto createRequest) {
        categoryAdapter.categoryAdd(createRequest);
    }

    @Override
    public void modifyCategory(CategoryModifyRequestDto modifyRequest) {
        categoryAdapter.categoryModify(modifyRequest);
    }

    @Override
    public void removeCategory(Integer categoryNo) {
        categoryAdapter.categoryRemove(categoryNo);
    }

    private void flatCategories(List<CategoryResponseDto> flattenCategories,
                                List<CategoryResponseDto> categories) {
        categories.forEach(category -> {
            if (category.getLowerCategories().isEmpty()) {
                flattenCategories.add(category);
            } else {
                flattenCategories.add(category);
                flatCategories(flattenCategories, category.getLowerCategories());
            }
        });
    }
}

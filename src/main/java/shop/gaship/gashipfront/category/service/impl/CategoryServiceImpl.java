package shop.gaship.gashipfront.category.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.category.adapter.CategoryAdapter;
import shop.gaship.gashipfront.category.dto.request.CategoryCreateRequestDto;
import shop.gaship.gashipfront.category.dto.request.CategoryModifyRequestDto;
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
    private final RedisTemplate redisTemplate;
    private final static String CATEGORY_KEY = "common_categories";

    @Override
    public CategoryResponseDto findCategory(Integer categoryNo) {
        return categoryAdapter.categoryDetails(categoryNo);
    }

    @Override
    public List<CategoryResponseDto> findCategories() {
        List<CategoryResponseDto> categories =
                (List<CategoryResponseDto>) redisTemplate.opsForValue().get(CATEGORY_KEY);

        if (Objects.isNull(categories)) {
            categories = categoryAdapter.categoryList();
            redisTemplate.opsForValue().set(CATEGORY_KEY, categories);
            redisTemplate.expire(CATEGORY_KEY, 60, TimeUnit.SECONDS);
        }
        return categories;
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

package shop.gaship.gashipfront.category.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipfront.category.adapter.CategoryAdapter;
import shop.gaship.gashipfront.category.dto.response.CategoryResponseDto;
import shop.gaship.gashipfront.category.service.CategoryService;

/**
 * 카테고리 api 컨트롤러입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryApiController {
    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryResponseDto> categoryList() {
        return categoryService.findCategories();
    }
}

package shop.gaship.gashipfront.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import shop.gaship.gashipfront.category.service.CategoryService;

/**
 * 카테고리 컨트롤러입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

}

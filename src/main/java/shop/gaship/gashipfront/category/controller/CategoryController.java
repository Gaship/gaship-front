package shop.gaship.gashipfront.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.gaship.gashipfront.category.dto.request.CategoryCreateRequestDto;
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

    @GetMapping("/admin/categories")
    public String categoryList() {
        return "category/categoryList";
    }

    @GetMapping("/categories/add")
    public String categoryAddForm(@RequestParam Integer upperCategoryNo,
                                  Model model) {
        model.addAttribute("upperCategoryNo", upperCategoryNo);
        return "category/categoryAddForm";
    }

    @PostMapping("/categories/add")
    public String categoryAdd(@RequestParam String name,
                              @RequestParam Integer upperCategoryNo) {
        categoryService.addCategory(new CategoryCreateRequestDto(name, upperCategoryNo));
        return "redirect:/admin/categories";
    }
}

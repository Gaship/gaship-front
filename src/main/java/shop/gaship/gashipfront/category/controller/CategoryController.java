package shop.gaship.gashipfront.category.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.gaship.gashipfront.category.dto.request.CategoryCreateRequestDto;
import shop.gaship.gashipfront.category.dto.request.CategoryModifyRequestDto;
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
    public String categoryAddForm(@RequestParam String upperCategoryNo,
                                  Model model) {
        model.addAttribute("upperCategoryNo", upperCategoryNo);
        return "category/categoryAddForm";
    }

    @GetMapping("/categories/{categoryNo}/modify")
    public String categoryModifyForm(@PathVariable Integer categoryNo,
                                     Model model) {
        model.addAttribute("category", categoryService.findCategory(categoryNo));
        return "category/categoryModifyForm";
    }

    @PostMapping("/categories/add")
    public String categoryAdd(@RequestParam String name,
                              @RequestParam String upperCategoryNo) {
        if (upperCategoryNo.equals("null")) {
            upperCategoryNo = null;
        }

        categoryService.addCategory(new CategoryCreateRequestDto(name,
                NumberUtils.createInteger(upperCategoryNo)));
        return "redirect:/admin/categories";
    }

    @PostMapping("/categories/modify")
    public String categoryModify(@ModelAttribute CategoryModifyRequestDto modifyRequest) {
        categoryService.modifyCategory(modifyRequest);
        return "redirect:/admin/categories";
    }
}

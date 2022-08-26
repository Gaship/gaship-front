package shop.gaship.gashipfront.product.controller;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.gaship.gashipfront.product.adapter.ProductAdapter;
import shop.gaship.gashipfront.product.dto.response.ProductAllInfoResponseDto;
import shop.gaship.gashipfront.product.service.ProductService;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * 설명작성란
 *
 * @author 김민수
 * @since 1.0
 */
@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public String showProducts(@RequestParam("page")String page,
                               @RequestParam(value = "category", required = false)String category,
                               @RequestParam(value = "min-amount", required = false)String minAmount,
                               @RequestParam(value = "max-amount", required = false)String maxAmount,
                               Model model) {
        PageResponse<ProductAllInfoResponseDto> products = productService.productAllInfoByPageable(page, "12", category, minAmount, maxAmount);

        model.addAttribute("products", products.getContent());
        model.addAttribute("next", products.isNext());
        model.addAttribute("previous", products.isPrevious());
        model.addAttribute("totalPage", products.getTotalPages());
        model.addAttribute("pageNum", products.getNumber() + 1);
        model.addAttribute("previousPageNo", Integer.parseInt(page) - 1);
        model.addAttribute("nextPageNo", Integer.parseInt(page) + 1);
        StringBuilder queryParam = new StringBuilder()
            .append(Objects.isNull(category) ? "" : "&category=" + category)
            .append(Objects.isNull(minAmount) ? "" : "&min-amount=" + minAmount)
            .append(Objects.isNull(maxAmount) ? "" : "&max-amount=" + maxAmount);
        model.addAttribute("queryParam", queryParam.toString());
        model.addAttribute("uri", "/products");

        return "product/products";
    }
}

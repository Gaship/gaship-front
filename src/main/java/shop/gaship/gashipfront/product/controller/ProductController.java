package shop.gaship.gashipfront.product.controller;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.gaship.gashipfront.elastic.dto.response.SearchResponseDto;
import shop.gaship.gashipfront.elastic.service.SearchService;
import shop.gaship.gashipfront.product.dto.response.ProductAllInfoResponseDto;
import shop.gaship.gashipfront.product.service.ProductService;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * 상품과 관련된 요청을 처리하는 클래스입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final SearchService searchService;

    @GetMapping
    public String showProducts(@RequestParam("page")String page,
                           @RequestParam(value = "category", required = false)String category,
                           @RequestParam(value = "min-amount", required = false)String minAmount,
                           @RequestParam(value = "max-amount", required = false)String maxAmount,
                           Model model) {
        PageResponse<ProductAllInfoResponseDto> products =
            productService.productAllInfoByPageable(page, "12", category, minAmount, maxAmount);

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


    @GetMapping(params = "category")
    public String findProductCategoryByPageable(
        Pageable page, @RequestParam(value = "category")String category, Model model) {
        PageResponse<ProductAllInfoResponseDto> products =
            productService.productCategoryByPageable(page, category);

        model.addAttribute("products", products.getContent());
        model.addAttribute("next", products.isNext());
        model.addAttribute("previous", products.isPrevious());
        model.addAttribute("totalPage", products.getTotalPages());
        model.addAttribute("pageNum", products.getNumber() + 1);
        model.addAttribute("previousPageNo", page.getPageNumber() - 1);
        model.addAttribute("nextPageNo", page.getPageNumber() + 1);
        model.addAttribute("uri", "/products");

        return "product/products";
    }

    @GetMapping(params = "keyword")
    public String findProductsByKeyword(
        Pageable page, @RequestParam(value = "keyword")String keyword, Model model) {
        List<Integer> searchResult = searchService.searchProductKeyword(keyword).stream()
            .map(SearchResponseDto::getId)
            .collect(Collectors.toUnmodifiableList());
        List<ProductAllInfoResponseDto> keywordProducts =
            productService.findProductNosList(searchResult);
        Page<ProductAllInfoResponseDto> products =
            new PageImpl<>(keywordProducts, page, keywordProducts.size());

        model.addAttribute("products", products.getContent());
        model.addAttribute("next", products.hasNext());
        model.addAttribute("previous", products.hasPrevious());
        model.addAttribute("totalPage", products.getTotalPages());
        model.addAttribute("pageNum", products.getNumber() + 1);
        model.addAttribute("previousPageNo", page.getPageNumber() - 1);
        model.addAttribute("nextPageNo", page.getPageNumber() + 1);
        model.addAttribute("uri", "/products");

        return "product/products";
    }

    @GetMapping("/{productNo}")
    public String findProductsByKeyword(@PathVariable("productNo")Integer productNo, Model model) {
        ProductAllInfoResponseDto product = productService.findProduct(productNo);
        model.addAttribute("product", product);

        return "product/productDetail";
    }

    @GetMapping("/add")
    public String productAddForm() {
        return "product/productAddForm";
    }
}

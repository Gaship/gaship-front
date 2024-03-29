package shop.gaship.gashipfront.product.controller;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import shop.gaship.gashipfront.category.service.CategoryService;
import shop.gaship.gashipfront.elastic.dto.response.SearchResponseDto;
import shop.gaship.gashipfront.elastic.service.SearchService;
import shop.gaship.gashipfront.product.dto.request.ProductCreateRequestDto;
import shop.gaship.gashipfront.product.dto.request.ProductModifyRequestDto;
import shop.gaship.gashipfront.product.dto.response.ProductAllInfoResponseDto;
import shop.gaship.gashipfront.product.dto.response.ProductByCategoryResponseDto;
import shop.gaship.gashipfront.product.service.ProductService;
import shop.gaship.gashipfront.statuscode.adapter.StatusCodeAdapter;
import shop.gaship.gashipfront.statuscode.enumm.DeliveryType;
import shop.gaship.gashipfront.statuscode.enumm.SalesStatus;
import shop.gaship.gashipfront.tag.service.TagService;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * 상품과 관련된 요청을 처리하는 클래스입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final SearchService searchService;
    private final CategoryService categoryService;
    private final StatusCodeAdapter statusCodeAdapter;
    private final TagService tagService;

    @GetMapping("/products")
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


    @GetMapping(value = "/products", params = "category")
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

    @GetMapping(value = "/products/category/{categoryNo}")
    public String findProductUpperCategory(Pageable page,
                                           @PathVariable("categoryNo") Integer categoryNo,
                                           Model model) {
        PageResponse<ProductByCategoryResponseDto> products = productService.findProductByCategory(page, categoryNo, null, null, true);

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

    @GetMapping(value = "/products", params = "keyword")
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

    @GetMapping("/products/{productNo}")
    public String findProductsByKeyword(@PathVariable("productNo")Integer productNo, Model model) {
        ProductAllInfoResponseDto product = productService.findProduct(productNo);
        model.addAttribute("product", product);

        return "product/productDetail";
    }

    @GetMapping("/products/add")
    public String productAddForm(Model model) {
        model.addAttribute("categories", categoryService.findFlattenCategories());
        model.addAttribute("deliveryTypes",
                statusCodeAdapter.getStatusCodeList(DeliveryType.GROUP));
        model.addAttribute("tags", tagService.findTags());
        return "product/productAddForm";
    }

    @PostMapping("/products/add")
    public String productAdd(List<MultipartFile> multipartFiles,
                             @ModelAttribute ProductCreateRequestDto createRequest) {
        productService.addProduct(multipartFiles, createRequest);
        return "redirect:/admin/products";
    }

    @GetMapping("/products/modify")
    public String productModifyForm(@RequestParam Integer productNo,
                                    Model model) {
        model.addAttribute("categories", categoryService.findFlattenCategories());
        model.addAttribute("deliveryTypes",
                statusCodeAdapter.getStatusCodeList(DeliveryType.GROUP));
        model.addAttribute("tags", tagService.findTags());
        model.addAttribute("product", productService.findProduct(productNo));
        return "product/productModifyForm";
    }

    @PostMapping(value = "/products/modify", params = "productNo")
    public String productModify(List<MultipartFile> multipartFiles,
                                @ModelAttribute ProductModifyRequestDto modifyRequest) {
        productService.modifyProduct(multipartFiles, modifyRequest);
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/products")
    public String showAdminProductList(@PageableDefault(size = 10) Pageable pageable,
                                       @RequestParam(value = "code", required = false) String code,
                                       Model model) {
        if (Objects.isNull(code) || code.isEmpty()) {
            model.addAttribute("products", productService.productAllInfoByPageable(
                    String.valueOf(pageable.getPageNumber()),
                    String.valueOf(pageable.getPageSize()),
                    null, null, null));
        } else {
            model.addAttribute("products", productService
                    .findProductListByCode(pageable, code));
            model.addAttribute("code", code);
        }

        model.addAttribute("salesStatusList",
                statusCodeAdapter.getStatusCodeList(SalesStatus.GROUP));
        return "product/adminProductList";
    }
}

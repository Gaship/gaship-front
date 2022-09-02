package shop.gaship.gashipfront.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipfront.product.dto.request.SalesStatusModifyRequestDto;
import shop.gaship.gashipfront.product.dto.response.ProductAllInfoResponseDto;
import shop.gaship.gashipfront.product.service.ProductService;
import shop.gaship.gashipfront.util.dto.PageResponse;

/**
 * 상품 ajax 요청 컨트롤러
 *
 * @author 김민수
 * @since 1.0
 */
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductRestController {
    private final ProductService productService;

    @GetMapping
    public PageResponse<ProductAllInfoResponseDto> findProductsByPageable(
        @RequestParam("page") String page, @RequestParam("size") String size,
        @RequestParam(value = "category", required = false)String category,
        @RequestParam(value = "min-amount", required = false)String minAmount,
        @RequestParam(value = "max-amount", required = false)String maxAmount) {
        return productService.findMainProducts(page, size, category, minAmount, maxAmount);
    }

    @PostMapping("/sales-status")
    public void modifySalesStatus(@RequestBody SalesStatusModifyRequestDto salesStatusModifyRequest) {
        productService.modifySalesStatus(salesStatusModifyRequest);
    }
}

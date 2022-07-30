package shop.gaship.gashipfront.cart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import shop.gaship.gashipfront.cart.dto.request.CartDeleteRequestDto;
import shop.gaship.gashipfront.cart.dto.request.CartModifyRequestDto;
import shop.gaship.gashipfront.cart.dto.request.CartProductQuantityUpDownRequestDto;
import shop.gaship.gashipfront.cart.dto.request.CartRequestDto;
import shop.gaship.gashipfront.cart.service.CartService;

/**
 * 장바구니를 구현하기 위한 컨트롤러 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {
    private final CartService cartService;

    /**
     * 상품상세 페이지에서 보증기간과 수량을 선택하고 카트에 물건을 추가하는 컨트롤러입니다.
     *
     * @param request 보증기간, 수량이 들어있습니다.
     * @return 반환값은 Void 타입의 ResponseEntity 입니다.
     */
    @PostMapping
    public ResponseEntity<Void> addToCart(@RequestBody CartRequestDto request) {
        cartService.addProductToCart(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    /**
     * 장바구니 페이지에서 수량을 변경하는 컨트롤러입니다.
     *
     * @param request 상품을 식별하기 위한 id 값과 보증기간 그리고 변경하길 원하는 제품의 최종 수량이 들어있습니다.
     * @return 반환값은 Void 타입의 ResponseEntity 입니다.
     */
    @PutMapping("/{cartsId}/products/{productsId}")
    public ResponseEntity<Void> modifyFromCart(@RequestBody CartModifyRequestDto request) {
        cartService.modifyProductQuantityFromCart(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    /**
     * 장바구니 페이지에서 상품의 수량을  +1 하는 컨트롤러입니다.
     *
     * @param request cartId 와 상품을 식별하기 위한 id 값과 보증기간이 있는 객체입니다.
     * @return 반환값은 Void 타입의 ResponseEntity 입니다.
     */
    @PutMapping("/{cartsId}/products/{productsId}/increase")
    public ResponseEntity<Void> increaseProductQuantityFromCart(@RequestBody CartProductQuantityUpDownRequestDto request) {
        cartService.increaseProductQuantityFromCart(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    /**
     * 장바구니 페이지에서 상품의 수량을 -1 하는 컨트롤러입니다.
     *
     * @param request cartId 와 상품을 식별하기 위한 id 값과 보증기간이 있는 객체입니다.
     * @return 반환값은 Void 타입의 ResponseEntity 입니다.
     */
    @PutMapping("/{cartsId}/products/{productsId}/decrease")
    public ResponseEntity<Void> decreaseProductQuantityFromCart(@RequestBody CartProductQuantityUpDownRequestDto request) {
        cartService.decreaseProductQuantityFromCart(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    /**
     * 장바구니 페이지에서 해당 상품을 삭제하는 컨트롤러 입니다.
     *
     * @param request cartId 와 상품을 식별하기 위한 id 값과 보증기간이 있는 객체입니다.
     * @return 반환값은 Void 타입의 ResponseEntity 입니다.
     */
    @DeleteMapping("/{cartsId}/products/{productsId}")
    public ResponseEntity<Void> deleteFromCart(@RequestBody CartDeleteRequestDto request) {
        cartService.deleteProductFromCart(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    /**
     * 장바구니를 보여주는 컨트롤러 입니다.
     *
     * @param cartId 조회하려는 장바구니의 id 값입니다.
     * @return 반환값은 Void 타입의 ResponseEntity 입니다.
     */
    @GetMapping("/{cartId}")
    public ResponseEntity<Void> getProductsFromCart(@PathVariable String cartId) {
        cartService.getProductsFromCart(cartId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}

package shop.gaship.gashipfront.cart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import shop.gaship.gashipfront.cart.dto.request.CartDeleteRequestDto;
import shop.gaship.gashipfront.cart.dto.request.CartModifyRequestDto;
import shop.gaship.gashipfront.cart.dto.request.CartProductQuantityUpDownRequestDto;
import shop.gaship.gashipfront.cart.dto.request.CartRequestDto;
import shop.gaship.gashipfront.cart.service.CartService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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
    private static final String NON_MEMBER_CART_ID = "NONMEMBERCARTID";
//    private static final String MEMBER_CART_ID = "MEMBERCARTID";
    private final CartService cartService;

    /**
     * 상품상세 페이지에서 보증기간과 수량을 선택하고 카트에 물건을 추가하는 컨트롤러입니다.
     *
     * (비회원, 회원) 장바구니 쿠키가 없을 때 (js 에서 쿠키가 없으면 이 url 을 보여줍니다.)
     * 장바구니 쿠키를 설정해주고 해당 쿠키에 추가하려는 물품정보를 추가합니다.
     *
     * @param request 상품Id, 보증기간, 수량이 들어있습니다.
     * @param response 리스폰스 입니다.
     * @return 반환값은 Void 타입의 ResponseEntity 입니다.
     */
    @PostMapping
    public ResponseEntity<Void> noneCookieAddToCart(@RequestBody CartRequestDto request,
                                                    HttpServletResponse response) {
        String nonMemberCartId = UUID.randomUUID().toString();
        cartService.addProductToCart(nonMemberCartId, request);
        response.addCookie(new Cookie(NON_MEMBER_CART_ID,nonMemberCartId));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    /**
     * 상품상세 페이지에서 보증기간과 수량을 선택하고 카트에 물건을 추가하는 컨트롤러입니다.
     *
     * (비회원, 회원) 장바구니 쿠키가 있을 때 (js 에서 쿠키가 있으면 이 url 을 보여줍니다.)
     * 해당 쿠키 id 값을 키로 가진 레디스 저장소에 상품 정보를 저장합니다.
     *
     * @param request 상품Id, 보증기간, 수량이 들어있습니다.
     * @return 반환값은 Void 타입의 ResponseEntity 입니다.
     */
    @PostMapping("/{cartId}")
    public ResponseEntity<Void> addToCart(@RequestBody CartRequestDto request,
                                          @PathVariable("cartId") String cartId) {
        cartService.addProductToCart(cartId, request);

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
    @PutMapping("/{cartId}/products/{productsId}")
    public ResponseEntity<Void> modifyFromCart(@RequestBody CartModifyRequestDto request,
                                               @PathVariable("cartId") String cartId) throws Exception {
        cartService.modifyProductQuantityFromCart(cartId, request);

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
    @PutMapping("/{cartId}/products/{productsId}/increase")
    public ResponseEntity<Void> increaseProductQuantityFromCart(@RequestBody CartProductQuantityUpDownRequestDto request,
                                                                @PathVariable("cartId") String cartId) {
        cartService.increaseProductQuantityFromCart(cartId, request);

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
    @PutMapping("/{cartId}/products/{productsId}/decrease")
    public ResponseEntity<Void> decreaseProductQuantityFromCart(@RequestBody CartProductQuantityUpDownRequestDto request,
                                                                @PathVariable("cartId") String cartId) {
        cartService.decreaseProductQuantityFromCart(cartId, request);

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
    @DeleteMapping("/{cartId}/products/{productsId}")
    public ResponseEntity<Void> deleteFromCart(@RequestBody CartDeleteRequestDto request,
                                               @PathVariable("cartId") String cartId) {
        cartService.deleteProductFromCart(cartId, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    /**
     * 회원, 비회원 쿠키가 없는 유저에게 장바구니를 보여주는 컨트롤러 입니다.
     * 아무상품도 담겨있지 않은 장바구니를 리턴해줍니다.
     *
     * @param response 리스폰스 입니다.
     * @return 반환값은 Void 타입의 ResponseEntity 입니다.
     */
    @GetMapping
    public ResponseEntity<Void> noneCartCookieGetProductsFromCart(HttpServletResponse response) {
        response.addCookie(new Cookie(NON_MEMBER_CART_ID,UUID.randomUUID().toString()));

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    /**
     * 회원 또는 비회원 쿠키가 있는 유저에게 장바구니를 보여주는 컨트롤러 입니다.
     *
     * @param cartId 조회하려는 장바구니의 id 값입니다.
     * @return 반환값은 Void 타입의 ResponseEntity 입니다.
     */
    @GetMapping("/{cartId}")
    public ResponseEntity<List<Objects>> getProductsFromCart(@PathVariable("cartId") String cartId) {
        List<Objects> productList = cartService.getProductsFromCart(cartId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(productList);
    }
}

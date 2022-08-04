package shop.gaship.gashipfront.cart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shop.gaship.gashipfront.cart.dto.request.CartDeleteRequestDto;
import shop.gaship.gashipfront.cart.dto.request.CartModifyRequestDto;
import shop.gaship.gashipfront.cart.dto.request.CartProductQuantityUpDownRequestDto;
import shop.gaship.gashipfront.cart.dto.request.CartRequestDto;
import shop.gaship.gashipfront.cart.service.CartService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
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
    private static final String MEMBER_CART_ID = "MEMBERCARTID";
    private final CartService cartService;
    private String cartId;

    /**
     * 상품상세 페이지에서 보증기간과 수량을 선택하고 장바구니에 물건을 추가하는 컨트롤러입니다.
     * 쿠키의 비회원, 회원 장바구니 존재 여부가 분기문 조건입니다.
     * 쿠키 id 값을 키로, 장바구니에 물건을 추가합니다.
     *
     * @param request         상품Id, 보증기간, 수량이 들어있습니다.
     * @param nonMemberCartId 비회원 장바구니의 id 값 입니다.
     * @param memberCartId    회원 장바구니의 id 값 입니다.
     * @return 반환값은 Void 타입의 ResponseEntity 입니다.
     * @author 최정우
     */
    @PostMapping
    public ResponseEntity<Void> addToCart(@RequestBody CartRequestDto request,
                                          @CookieValue(value = NON_MEMBER_CART_ID, required = false) String nonMemberCartId,
                                          @CookieValue(value = MEMBER_CART_ID, required = false) String memberCartId,
                                          HttpServletResponse response) {
        cartId = assignCartId(nonMemberCartId, memberCartId);
        if (Objects.isNull(cartId)) {
            cartId = UUID.randomUUID().toString();
            response.addCookie(new Cookie(NON_MEMBER_CART_ID, cartId));
        }
        cartService.addProductToCart(cartId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    /**
     * 장바구니 페이지에서 수량을 변경하는 컨트롤러입니다.
     *
     * @param request         상품을 식별하기 위한 id 값과 보증기간 그리고 변경하길 원하는 제품의 최종 수량이 들어있습니다.
     * @param nonMemberCartId 비회원 장바구니의 id 값 입니다.
     * @param memberCartId    회원 장바구니의 id 값 입니다.
     * @return 반환값은 Void 타입의 ResponseEntity 입니다.
     * @author 최정우
     */
    @PutMapping("/products")
    public ResponseEntity<Void> modifyFromCart(@RequestBody CartModifyRequestDto request,
                                               @CookieValue(value = NON_MEMBER_CART_ID, required = false) String nonMemberCartId,
                                               @CookieValue(value = MEMBER_CART_ID, required = false) String memberCartId) throws Exception {
        cartId = assignCartId(nonMemberCartId, memberCartId);
        cartService.modifyProductQuantityFromCart(cartId, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    /**
     * 장바구니 페이지에서 상품의 수량을  +1 하는 컨트롤러입니다.
     *
     * @param request         cartId 와 상품을 식별하기 위한 id 값과 보증기간이 있는 객체입니다.
     * @param nonMemberCartId 비회원 장바구니의 id 값 입니다.
     * @param memberCartId    회원 장바구니의 id 값 입니다.
     * @return 반환값은 Void 타입의 ResponseEntity 입니다.
     * @author 최정우
     */
    @PutMapping("/products/increase")
    public ResponseEntity<Void> increaseProductQuantityFromCart(
            @RequestBody CartProductQuantityUpDownRequestDto request,
            @CookieValue(value = NON_MEMBER_CART_ID, required = false) String nonMemberCartId,
            @CookieValue(value = MEMBER_CART_ID, required = false) String memberCartId) {
        cartId = assignCartId(nonMemberCartId, memberCartId);
        cartService.increaseProductQuantityFromCart(cartId, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    /**
     * 장바구니 페이지에서 상품의 수량을 -1 하는 컨트롤러입니다.
     *
     * @param request         cartId 와 상품을 식별하기 위한 id 값과 보증기간이 있는 객체입니다.
     * @param nonMemberCartId 비회원 장바구니의 id 값 입니다.
     * @param memberCartId    회원 장바구니의 id 값 입니다.
     * @return 반환값은 Void 타입의 ResponseEntity 입니다.
     * @author 최정우
     */
    @PutMapping("/products/decrease")
    public ResponseEntity<Void> decreaseProductQuantityFromCart(@RequestBody CartProductQuantityUpDownRequestDto request,
                                                                @CookieValue(value = NON_MEMBER_CART_ID, required = false) String nonMemberCartId,
                                                                @CookieValue(value = MEMBER_CART_ID, required = false) String memberCartId) throws Exception {
        cartId = assignCartId(nonMemberCartId, memberCartId);
        cartService.decreaseProductQuantityFromCart(cartId, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    /**
     * 장바구니 페이지에서 해당 상품을 삭제하는 컨트롤러 입니다.
     *
     * @param request         cartId 와 상품을 식별하기 위한 id 값과 보증기간이 있는 객체입니다.
     * @param nonMemberCartId 비회원 장바구니의 id 값 입니다.
     * @param memberCartId    회원 장바구니의 id 값 입니다.
     * @return 반환값은 Void 타입의 ResponseEntity 입니다.
     * @author 최정우
     */
    @DeleteMapping("/products/delete")
    public ResponseEntity<Void> deleteFromCart(@RequestBody CartDeleteRequestDto request,
                                               @CookieValue(value = NON_MEMBER_CART_ID, required = false) String nonMemberCartId,
                                               @CookieValue(value = MEMBER_CART_ID, required = false) String memberCartId) throws Exception {
        cartId = assignCartId(nonMemberCartId, memberCartId);
        cartService.deleteProductFromCart(cartId, request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    /**
     * 장바구니를 보여주는 컨트롤러 입니다.
     *
     * @param nonMemberCartId 비회원 장바구니의 id 값 입니다.
     * @param memberCartId    회원 장바구니의 id 값 입니다.
     * @param response        리스폰스 입니다.
     * @param model           model 입니다.
     * @return 장바수니 html 을 반환합니다.
     * @author 최정우
     */
    @GetMapping
    public String getProductsFromCart(@CookieValue(value = NON_MEMBER_CART_ID, required = false) String nonMemberCartId,
                                      @CookieValue(value = MEMBER_CART_ID, required = false) String memberCartId,
                                      HttpServletResponse response,
                                      Model model) {
        cartId = assignCartId(nonMemberCartId, memberCartId);
        if (Objects.isNull(cartId)) {
            cartId = UUID.randomUUID().toString();
            response.addCookie(new Cookie(NON_MEMBER_CART_ID, cartId));
        }
        model.addAttribute(cartService.getProductsFromCart(cartId));

        return "/carts";
    }

    private String assignCartId(String nonMemberCartId, String memberCartId) {
        if (Objects.isNull(nonMemberCartId) && Objects.isNull(memberCartId)) {
            return null;
        } else if (Objects.nonNull(nonMemberCartId)) {
            cartId = nonMemberCartId;
        } else {
            cartId = memberCartId;
        }
        return cartId;
    }
}

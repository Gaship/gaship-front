package shop.gaship.gashipfront.cart.controller;

import java.util.Objects;
import java.util.UUID;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shop.gaship.gashipfront.cart.dto.request.CartProductDeleteRequestDto;
import shop.gaship.gashipfront.cart.dto.request.CartProductModifyRequestDto;
import shop.gaship.gashipfront.cart.exception.CartProductAmountException;
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
    private static final String CART_ID = "CID";
    private final CartService cartService;

    /**
     * 상품상세 페이지에서 보증기간과 수량을 선택하고 장바구니에 물건을 추가하는 컨트롤러입니다.
     * 쿠키 id 값을 키로, 장바구니에 물건을 추가합니다.
     *
     * @param request 상품Id, 보증기간, 수량이 들어있습니다.
     * @param cartId  장바구니 id
     * @return 반환값은 Void 타입의 ResponseEntity 입니다.
     * @author 최정우
     */
    @PostMapping("/add-product")
    @ResponseBody
    public Integer addToCart(@RequestBody CartProductModifyRequestDto request,
                             @CookieValue(value = CART_ID, required = false) String cartId,
                             HttpSession session,
                             HttpServletResponse response,
                             RedirectAttributes attributes) throws CartProductAmountException {
        session.getAttribute("");
        if (Objects.isNull(cartId)) {
            String newCartId = UUID.randomUUID().toString();
            response.addCookie(new Cookie(CART_ID, newCartId));
            cartId = newCartId;
        }
        Integer orderQuantity = cartService.modifyProductQuantityFromCart(cartId, request);
        if (orderQuantity == 0) {
            attributes.addAttribute("cartMax", true);
        }
        attributes.addAttribute("status", true);
        return orderQuantity;
    }

    /**
     * 장바구니 페이지에서 수량을 변경하는 컨트롤러입니다.
     *
     * @param request 상품 id 값, 상품수량이 들어있는 객체
     * @param cartId  장바구니 id
     * @author 최정우
     */
    @PutMapping("/modify-quantity")
    @ResponseBody
    public Integer modifyFromCart(
            @RequestBody CartProductModifyRequestDto request,
            @CookieValue(value = CART_ID) String cartId,
            HttpSession session)
            throws CartProductAmountException {
        session.getAttribute("");
        return cartService.modifyProductQuantityFromCart(cartId, request);
    }

    /**
     * 장바구니 페이지에서 해당 상품을 삭제하는 컨트롤러 입니다.
     *
     * @param request 상품 id 값
     * @param cartId  장바구니 id
     * @return 반환값은 Void 타입의 ResponseEntity 입니다.
     * @author 최정우
     */
    @DeleteMapping("/delete-product")
    @ResponseBody
    public boolean deleteFromCart(
            @RequestBody CartProductDeleteRequestDto request,
            @CookieValue(value = CART_ID) String cartId) throws CartProductAmountException {
        cartService.deleteProductFromCart(cartId, request);
        return true;
    }

    /**
     * 장바구니를 보여주는 컨트롤러 입니다.
     *
     * @param cartId   장바구니의 id 값 입니다.
     * @param response 리스폰스 입니다.
     * @param model    model 입니다.
     * @return 장바구니 페이지 목록페이지
     * @author 최정우
     */
    @GetMapping
    public String getProductsFromCart(@CookieValue(value = CART_ID, required = false) String cartId,
                                      HttpServletResponse response,
                                      Model model) {
        if (Objects.isNull(cartId)) {
            String newCartId = UUID.randomUUID().toString();
            response.addCookie(new Cookie(CART_ID, newCartId));
            return "carts";
        }
        model.addAttribute(cartService.getProductsFromCart(cartId));

        return "carts";
    }
}

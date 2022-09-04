package shop.gaship.gashipfront.cart.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import shop.gaship.gashipfront.cart.dto.request.CartProductModifyRequestDto;
import shop.gaship.gashipfront.cart.exception.CartProductAmountException;
import shop.gaship.gashipfront.cart.service.CartService;

import javax.servlet.http.HttpServletRequest;


/**
 * 장바구니를 구현하기 위한 컨트롤러 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/carts")
@Slf4j
public class CartController {
    private static final String CART_ID = "CID";
    private final CartService cartService;

    /**
     * 상품상세 페이지에서 보증기간과 수량을 선택하고 장바구니에 물건을 추가하는 컨트롤러입니다.
     * 쿠키 id 값을 키로, 장바구니에 물건을 추가합니다.
     *
     * @param request 상품Id, 보증기간, 수량이 들어있습니다.
     * @return 반환값은 Void 타입의 ResponseEntity 입니다.
     * @author 최정우
     */
    @PostMapping("/add-product")
    @ResponseBody
    public Integer addToCart(@ModelAttribute CartProductModifyRequestDto request,
                             HttpServletRequest servletRequest) throws CartProductAmountException {
        String cartId = (String) servletRequest.getAttribute(CART_ID);
        return cartService.addProductToCart(cartId, request);
    }

    /**
     * 장바구니 페이지에서 수량을 변경하는 컨트롤러입니다.
     *
     * @author 최정우
     */
    @PutMapping("/modify-quantity")
    @ResponseBody
    public boolean modifyFromCart(HttpServletRequest servletRequest,
                                 @RequestParam Long productNo,
                                 @RequestParam Long productQuantity) throws CartProductAmountException {
        String cartId = (String) servletRequest.getAttribute(CART_ID);
        cartService.modifyProductQuantityFromCart(cartId, productNo, productQuantity);
        return true;
    }

    /**
     * 장바구니 페이지에서 해당 상품을 삭제하는 컨트롤러 입니다.
     *
     * @return 반환값은 Void 타입의 ResponseEntity 입니다.
     * @author 최정우
     */
    @DeleteMapping("/delete-product")
    public String deleteFromCart(HttpServletRequest servletRequest,
                                 @RequestParam Long id) throws CartProductAmountException {
        String cartId = (String) servletRequest.getAttribute(CART_ID);
        cartService.deleteProductFromCart(cartId, id);
        return "redirect:/carts";
    }

    /**
     * 장바구니를 보여주는 컨트롤러 입니다.
     *
     * @param model model 입니다.
     * @return 장바구니 페이지 목록페이지
     * @author 최정우
     */
    @GetMapping
    public String getProductsFromCart(HttpServletRequest request,
                                      Model model) {
        String cartId = (String) request.getAttribute(CART_ID);
        model.addAttribute("response", cartService.getProductsFromCart(cartId));
        return "cart/carts";
    }
}

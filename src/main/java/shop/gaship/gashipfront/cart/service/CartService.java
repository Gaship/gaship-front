package shop.gaship.gashipfront.cart.service;

import shop.gaship.gashipfront.cart.dto.request.CartDeleteRequestDto;
import shop.gaship.gashipfront.cart.dto.request.CartModifyRequestDto;
import shop.gaship.gashipfront.cart.dto.request.CartProductQuantityUpDownRequestDto;
import shop.gaship.gashipfront.cart.dto.request.CartRequestDto;

import java.util.List;
import java.util.Objects;

/**
 * 장바구니의 service 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
public interface CartService {
    /**
     * 비회원인 사람이 장바구니에 상품을 추가하는 메서드입니다.
     *
     * @param request 등록에 사용되는 정보 객체입니다.
     * @author 최정우
     */
    void addProductToCart(String cartId, CartRequestDto request);

    /**
     * 장바구니에 담긴 상품의 수량을 변경하는 메서드입니다.
     *
     * @param request 변경하려는 대상의 정보가 담기는 객체입니다.
     * @author 최정우
     */
    void modifyProductQuantityFromCart(String cartId, CartModifyRequestDto request) throws Exception;

    /**
     * 장바구니에 담긴 상품의 수량을 +1 메서드입니다.
     *
     * @param request 변경하려는 대상의 정보가 담기는 객체입니다.
     * @author 최정우
     */
    void increaseProductQuantityFromCart(String cartId, CartProductQuantityUpDownRequestDto request);

    /**
     * 장바구니에 담긴 상품의 수량을 -1 메서드입니다.
     *
     * @param request 변경하려는 대상의 정보가 담기는 객체입니다.
     * @author 최정우
     */
    void decreaseProductQuantityFromCart(String cartId, CartProductQuantityUpDownRequestDto request);

    /**
     * 장바구니에서 상품을 삭제하는 메서드입니다.
     *
     * @param request 삭제할 상품의 정보를 담은 객체입니다.
     * @author 최정우
     */
    void deleteProductFromCart(String cartId, CartDeleteRequestDto request);

    /**
     * 장바구니에 담긴 상품들의 정보를 얻는 메서드 입니다.
     *
     * @param cartId 삭제할 상품의 정보를 담은 객체입니다.
     * @return
     * @author 최정우
     */
    List<Objects> getProductsFromCart(String cartId);
}

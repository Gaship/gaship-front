package shop.gaship.gashipfront.cart.service;

import shop.gaship.gashipfront.cart.dto.request.CartDeleteRequestDto;
import shop.gaship.gashipfront.cart.dto.request.CartProductQuantityUpDownRequestDto;
import shop.gaship.gashipfront.cart.dto.request.CartRequestDto;

/**
 * 장바구니의 service 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
public interface CartService {
    /**
     * 장바구니에 상품을 추가하는 메서드입니다.
     *
     * @param request 등록에 사용되는 정보 객체입니다.
     */
    void addProductToCart(CartRequestDto request);

    /**
     * 장바구니에 담긴 상품의 수량을 변경하는 메서드입니다.
     *
     * @param request 변경하려는 대상의 정보가 담기는 객체입니다.
     */
    void modifyProductQuantityFromCart(CartRequestDto request);

    /**
     * 장바구니에 담긴 상품의 수량을 +1 메서드입니다.
     *
     * @param request 변경하려는 대상의 정보가 담기는 객체입니다.
     */
    void increaseProductQuantityFromCart(CartProductQuantityUpDownRequestDto request);

    /**
     * 장바구니에 담긴 상품의 수량을 -1 메서드입니다.
     *
     * @param request 변경하려는 대상의 정보가 담기는 객체입니다.
     */
    void decreaseProductQuantityFromCart(CartProductQuantityUpDownRequestDto request);

    /**
     * 장바구니에서 상품을 삭제하는 메서드입니다.
     *
     * @param request 삭제할 상품의 정보를 담은 객체입니다.
     */
    void deleteProductFromCart(CartDeleteRequestDto request);

    /**
     * 장바구니에 담긴 상품들의 정보를 얻는 메서드 입니다.
     *
     * @param cartId 삭제할 상품의 정보를 담은 객체입니다.
     */
    void getProductsFromCart(String cartId);
}

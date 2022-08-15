package shop.gaship.gashipfront.cart.service;

import java.util.List;
import shop.gaship.gashipfront.cart.dto.request.CartProductDeleteRequestDto;
import shop.gaship.gashipfront.cart.dto.request.CartProductModifyRequestDto;
import shop.gaship.gashipfront.cart.dto.response.ProductResponseDto;
import shop.gaship.gashipfront.cart.exception.CartProductAmountException;



/**
 * 장바구니의 service 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
public interface CartService {
    /**
     * 장바구니에 담긴 상품의 수량을 변경하는 메서드입니다.
     *
     * @param cartId  장바구니 id 값입니다.
     * @param request 장바구니에 담을 상품의 정보(상품 id , 보증기간, 갯수)가 담겨 있습니다.
     * @author 최정우
     */
    Integer modifyProductQuantityFromCart(
            String cartId, CartProductModifyRequestDto request) throws CartProductAmountException;

    /**
     * 장바구니에서 상품을 삭제하는 메서드입니다.
     *
     * @param cartId  장바구니 id 값입니다.
     * @param request 삭제할 상품의 정보를 담은 객체입니다.
     * @author 최정우
     */
    void deleteProductFromCart(String cartId, CartProductDeleteRequestDto request)
            throws CartProductAmountException;

    /**
     * 비회원의 장바구니 상품 목록을 회원의 장바구니에 옮기는 메서드 입니다.
     *
     * @param nonMemberCartId 비회원일 때의 장바구니 id
     * @param memberId        회원일 때의 장바구니 id
     * @author 최정우
     */
    void mergeCart(String nonMemberCartId, Integer memberId);

    /**
     * 장바구니에 담긴 상품들의 정보를 얻는 메서드 입니다.
     *
     * @param cartId 삭제할 상품의 정보를 담은 객체입니다.
     * @return 장바구니 상품 리스트를 반환합니다.
     * @author 최정우
     */
    List<ProductResponseDto> getProductsFromCart(String cartId);

}

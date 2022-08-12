package shop.gaship.gashipfront.cart.service;

import shop.gaship.gashipfront.cart.dto.request.CartProductAddRequestDto;
import shop.gaship.gashipfront.cart.dto.request.CartProductDeleteRequestDto;
import shop.gaship.gashipfront.cart.dto.request.CartProductModifyRequestDto;
import shop.gaship.gashipfront.cart.dto.request.CartProductQuantityUpDownRequestDto;

import java.util.List;

/**
 * 장바구니의 service 입니다.
 * @author 최정우
 * @since 1.0
 */
public interface CartService {
    /**
     * 비회원인 사람이 장바구니에 상품을 추가하는 메서드입니다.
     * @param cartId  장바구니 id 값입니다.
     * @param request 등록에 사용되는 정보 객체입니다.
     * @author 최정우
     */
    void addProductToCart(String cartId, CartProductAddRequestDto request);

    /**
     * 장바구니에 담긴 상품의 수량을 변경하는 메서드입니다.
     * @param cartId  장바구니 id 값입니다.
     * @param request 장바구니에 담을 상품의 정보(상품 id , 보증기간, 갯수)가 담겨 있습니다.
     * @author 최정우
     */
    void modifyProductQuantityFromCart(String cartId, CartProductModifyRequestDto request) throws Exception;

    /**
     * 장바구니에 담긴 상품의 수량을 +1 메서드입니다.
     *@param cartId  장바구니 id 값입니다.
     * @param request 변경하려는 대상의 정보가 담기는 객체입니다.
     * @author 최정우
     */
    void increaseProductQuantityFromCart(String cartId, CartProductQuantityUpDownRequestDto request);

    /**
     * 장바구니에 담긴 상품의 수량을 -1 메서드입니다.
     * @param cartId 수량을 변경하려는 카트의 아이디 입니다.
     * @param request 변경하려는 대상의 정보가 담기는 객체입니다.
     * @author 최정우
     */
    void decreaseProductQuantityFromCart(String cartId, CartProductQuantityUpDownRequestDto request) throws Exception;

    /**
     * 장바구니에서 상품을 삭제하는 메서드입니다.
     * @param cartId  장바구니 id 값입니다.
     * @param request 삭제할 상품의 정보를 담은 객체입니다.
     * @author 최정우
     */
    void deleteProductFromCart(String cartId, CartProductDeleteRequestDto request) throws Exception;

    /**
     * 비회원의 장바구니 상품 목록을 회원의 장바구니에 옮기는 메서드 입니다.
     * @param nonMemberCartId 비회원일 때의 장바구니 id
     * @param memberId        회원일 때의 장바구니 id
     * @author 최정우
     */
    void mergeCart(String nonMemberCartId, Integer memberId);

    /**
     * 장바구니에 담긴 상품들의 정보를 얻는 메서드 입니다.
     * @param cartId 삭제할 상품의 정보를 담은 객체입니다.
     * @return
     * @author 최정우
     */
    List<Integer> getProductsFromCart(String cartId);

}

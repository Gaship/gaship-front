package shop.gaship.gashipfront.cart.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.gaship.gashipfront.cart.dto.request.CartDeleteRequestDto;
import shop.gaship.gashipfront.cart.dto.request.CartModifyRequestDto;
import shop.gaship.gashipfront.cart.dto.request.CartProductQuantityUpDownRequestDto;
import shop.gaship.gashipfront.cart.dto.request.CartRequestDto;
import shop.gaship.gashipfront.cart.exception.IllegalQuantityException;
import shop.gaship.gashipfront.cart.service.CartService;

import java.util.List;
import java.util.Objects;

/**
 * The type Cart service.
 *
 * @author 최정우
 * @since 1.0
 */
@Service
public class CartServiceImpl implements CartService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final HashOperations<String, String, Integer> hashOperations;

    private static final Integer PLUSONE = 1;
    private static final Integer MINUSONE = -1;

    @Autowired
    public CartServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    /**
     * {@inheritDoc}
     *
     * 장바구니에 상품을 추가하는 메서드 입니다.
     *
     * @param cartId 장바구니 id 값입니다.
     * @param request 장바구니에 담을 상품의 정보(상품 id , 보증기간, 갯수)가 담겨 있습니다.
     */
    @Transactional
    @Override
    public void addProductToCart(String cartId, CartRequestDto request) {
        String cartKey = cartId;
        String hashKey = request.getProductId().toString() + "-" + request.getCarePeriod().toString();
        Integer quantity = request.getQuantity();

        hashOperations.increment(cartKey, request.getProductId().toString(), quantity);
        hashOperations.put(cartKey, hashKey, quantity);
    }

    /**
     * {@inheritDoc}
     *
     * 장바구니에 담긴 상품의 갯수를 변경하는 메서드 입니다.
     *
     * @param cartId 장바구니 id 값입니다.
     * @param request 장바구니에 담을 상품의 정보(상품 id , 보증기간, 갯수)가 담겨 있습니다.
     */
    @Transactional
    @Override
    public void modifyProductQuantityFromCart(String cartId, CartModifyRequestDto request) throws Exception{
        String cartKey = cartId;
        String hashKey = request.getProductId().toString() + "-" + request.getCarePeriod().toString();

        Integer originQuantity = hashOperations.get(cartKey, hashKey);
        if (Objects.isNull(originQuantity)){
            throw new IllegalQuantityException();
        }
        hashOperations.increment(cartKey, request.getProductId().toString(), request.getQuantity()-originQuantity.longValue());
        hashOperations.put(cartKey, hashKey, request.getQuantity());
    }

    /**
     * {@inheritDoc}
     *
     * 장바구니에 담긴 상품의 수량을 +1 하는 메서드입니다.
     *
     * @param cartId 장바구니 id 값입니다.
     * @param request 장바구니에 담을 상품의 정보(상품 id , 보증기간)가 담겨 있습니다.
     */
    @Transactional
    @Override
    public void increaseProductQuantityFromCart(String cartId, CartProductQuantityUpDownRequestDto request) {
        String cartKey = cartId;
        String hashKey = request.getProductId().toString() + "-" + request.getCarePeriod().toString();

        hashOperations.increment(cartKey, request.getProductId().toString(), PLUSONE);
        hashOperations.increment(cartKey, hashKey, PLUSONE);
    }

    /**
     * {@inheritDoc}
     *
     * 장바구니에 담긴 상품의 수량을 -1 하는 메서드입니다.
     *
     * @param cartId 장바구니 id 값입니다.
     * @param request 장바구니에 담을 상품의 정보(상품 id , 보증기간)가 담겨 있습니다.
     */
    @Transactional
    @Override
    public void decreaseProductQuantityFromCart(String cartId, CartProductQuantityUpDownRequestDto request) {
        String cartKey = cartId;
        String hashKey = request.getProductId().toString() + "-" + request.getCarePeriod().toString();

        hashOperations.increment(cartKey, request.getProductId().toString(), MINUSONE);
        hashOperations.increment(cartKey, hashKey, MINUSONE);
    }

    /**
     * {@inheritDoc}
     *
     * 장바구니에 담긴 특정 상품을 삭제하는 메서드 입니다.
     *
     * @param cartId 장바구니 id 값입니다.
     * @param request 장바구니에 담을 상품의 정보(상품 id , 보증기간)가 담겨 있습니다.
     */
    @Transactional
    @Override
    public void deleteProductFromCart(String cartId, CartDeleteRequestDto request) {
        String cartKey = cartId;
        String hashKey = request.getProductId().toString() + "-" + request.getCarePeriod().toString();

        hashOperations.increment(cartKey, request.getProductId().toString(), MINUSONE);
        hashOperations.delete(cartKey, hashKey);
    }

    @Override
    public List<Objects> getProductsFromCart(String cartId) {
        return null;
    }
}

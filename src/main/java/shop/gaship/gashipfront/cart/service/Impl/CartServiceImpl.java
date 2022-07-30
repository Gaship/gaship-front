package shop.gaship.gashipfront.cart.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.cart.dto.request.CartDeleteRequestDto;
import shop.gaship.gashipfront.cart.dto.request.CartModifyRequestDto;
import shop.gaship.gashipfront.cart.dto.request.CartProductQuantityUpDownRequestDto;
import shop.gaship.gashipfront.cart.dto.request.CartRequestDto;
import shop.gaship.gashipfront.cart.service.CartService;

/**
 * @author 최정우
 * @since 1.0
 */
@Service
public class CartServiceImpl implements CartService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final HashOperations<String, String, Integer> hashOperations;

    private static final Integer MINUSONE = -1;
    private static final Integer PLUSONE = 1;
    @Autowired
    public CartServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void addProductToCart(CartRequestDto request) {
        String cartKey = request.getCartId();
        String hashKey = request.getProductId().toString() + "-" + request.getCarePeriod().toString();

        hashOperations.put(cartKey, hashKey, request.getQuantity());
    }

    @Override
    public void modifyProductQuantityFromCart(CartModifyRequestDto request) {
        String cartKey = request.getCartId();
        String hashKey = request.getProductId().toString() + "-" + request.getCarePeriod().toString();

        hashOperations.put(cartKey, hashKey, request.getQuantity());
    }

    @Override
    public void increaseProductQuantityFromCart(CartProductQuantityUpDownRequestDto request) {
        String cartKey = request.getCartId();
        String hashKey = request.getProductId().toString() + "-" + request.getCarePeriod().toString();

        hashOperations.increment(cartKey, hashKey, PLUSONE);
    }

    @Override
    public void decreaseProductQuantityFromCart(CartProductQuantityUpDownRequestDto request) {
        String cartKey = request.getCartId();
        String hashKey = request.getProductId().toString() + "-" + request.getCarePeriod().toString();

        hashOperations.increment(cartKey, hashKey, MINUSONE);
    }

    @Override
    public void deleteProductFromCart(CartDeleteRequestDto request) {
        String cartKey = request.getCartId();
        String hashKey = request.getProductId().toString() + "-" + request.getCarePeriod().toString();

        hashOperations.delete(cartKey, hashKey);

    }

    @Override
    public void getProductsFromCart(String cartId) {

    }
}

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
import shop.gaship.gashipfront.cart.exception.CartProductAmountException;
import shop.gaship.gashipfront.cart.exception.IllegalQuantityException;
import shop.gaship.gashipfront.cart.service.CartService;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * {@inheritDoc}
 * CartService 의 구현체입니다.
 */
@Service
public class CartServiceImpl implements CartService {
    private static final Integer PLUSONE = 1;
    private static final Integer MINUSONE = -1;
    private final RedisTemplate<String, Object> redisTemplate;
    private final HashOperations<String, String, Integer> hashOperations;

    @Autowired
    public CartServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void addProductToCart(String cartId, CartRequestDto request) {
        String cartKey = cartId;
        String hashKey = request.getProductId().toString() + "-" + request.getCarePeriod().toString();
        Integer quantity = request.getQuantity();

        hashOperations.increment(cartKey, hashKey, quantity);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void modifyProductQuantityFromCart(String cartId, CartModifyRequestDto request) {
        String cartKey = cartId;
        String hashKey = request.getProductId().toString() + "-" + request.getCarePeriod().toString();

        hashOperations.put(cartKey, hashKey, request.getQuantity());
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void increaseProductQuantityFromCart(String cartId, CartProductQuantityUpDownRequestDto request) {
        String cartKey = cartId;
        String hashKey = request.getProductId().toString() + "-" + request.getCarePeriod().toString();

        hashOperations.increment(cartKey, hashKey, PLUSONE);
    }

    /**
     * {@inheritDoc}
     *
     * @throws CartProductAmountException 담겨있는 상품의 갯수가 1이하이면 발생하는 오류
     */
    @Transactional
    @Override
    public void decreaseProductQuantityFromCart(String cartId, CartProductQuantityUpDownRequestDto request) throws CartProductAmountException {
        String cartKey = cartId;
        String hashKey = request.getProductId().toString() + "-" + request.getCarePeriod().toString();
        Integer cartProductAmount = hashOperations.get(cartKey, hashKey);

        if (cartProductAmount <= 1) {
            throw new CartProductAmountException();
        }
        hashOperations.increment(cartKey, hashKey, MINUSONE);
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalQuantityException 조회한 상품이 null 이면 오류
     */
    @Transactional
    @Override
    public void deleteProductFromCart(String cartId, CartDeleteRequestDto request) throws Exception {
        String cartKey = cartId;
        String productId = request.getProductId().toString();
        String hashKey = productId + "-" + request.getCarePeriod().toString();

        hashOperations.increment(cartKey, hashKey, MINUSONE);
        Integer productAndCarePeriodQuantity = hashOperations.get(cartKey, hashKey);
        if (Objects.isNull(productAndCarePeriodQuantity)) {
            throw new IllegalQuantityException();
        }
        if (productAndCarePeriodQuantity < 1) {
            hashOperations.delete(cartKey, hashKey);
        }
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void mergeCart(String nonMemberCartId, Integer memberId) {
        Map<String, Integer> map = hashOperations.entries(nonMemberCartId);
        String key = String.valueOf(memberId);
        mergeHashMap(key, map);
        hashOperations.delete(nonMemberCartId);
    }

    /**
     * 키 값이 다른 두개의 레디스를 합치는 메서드 입니다.
     */
    private void mergeHashMap(String key, Map<String, Integer> map) {
        map.forEach((key1, value) -> hashOperations.putIfAbsent(key, key1, value));
    }

    @Override
    public List<Integer> getProductsFromCart(String cartId) {
        return getDistinctIntProductId(cartId);
    }

    private List<Integer> getDistinctIntProductId(String cartId) {
        Set<String> keys = hashOperations.keys(cartId);
        return keys.stream()
                .map(key -> key.substring(0, key.lastIndexOf("-")))
                .distinct()
                .map(Integer::valueOf)
                .collect(Collectors.toList());



    }
}

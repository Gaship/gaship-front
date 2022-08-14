package shop.gaship.gashipfront.cart.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.gaship.gashipfront.cart.dto.request.CartProductDeleteRequestDto;
import shop.gaship.gashipfront.cart.dto.request.CartProductModifyRequestDto;
import shop.gaship.gashipfront.cart.exception.CartProductAmountException;
import shop.gaship.gashipfront.cart.service.CartService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * CartService 의 구현체입니다.
 */
@Service
public class CartServiceImpl implements CartService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final HashOperations<String, Integer, Integer> hashOperations;

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
    public Integer modifyProductQuantityFromCart(
            String cartNo, CartProductModifyRequestDto request) throws CartProductAmountException {
        if (request.getQuantity() > 10 || request.getQuantity() < 1) {
            throw new CartProductAmountException();
        }
        hashOperations.put(cartNo, request.getProductId(), request.getQuantity());
        return request.getQuantity();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void deleteProductFromCart(String cartNo, CartProductDeleteRequestDto request) {
        hashOperations.delete(cartNo, request.getProductId().toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mergeCart(String nonMemberCartId, Integer memberId) {
        Map<Integer, Integer> map = hashOperations.entries(nonMemberCartId);
        String key = String.valueOf(memberId);
        mergeHashMap(key, map);
        hashOperations.delete(nonMemberCartId);
    }

    /**
     * 키 값이 다른 두개의 레디스를 합치는 메서드 입니다.
     */
    private void mergeHashMap(String key, Map<Integer, Integer> map) {
        map.forEach((key1, value) -> hashOperations.putIfAbsent(key, key1, value));
    }

    @Override
    public List<Integer> getProductsFromCart(String cartId, Pageable pageable) {
        Map<Integer, Integer> products = hashOperations.entries(cartId);
        ArrayList<Integer> arrayList = new ArrayList<>(products.keySet());
        return null;
    }

}

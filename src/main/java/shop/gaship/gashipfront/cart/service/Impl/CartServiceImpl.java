package shop.gaship.gashipfront.cart.service.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.cart.dto.request.CartProductDeleteRequestDto;
import shop.gaship.gashipfront.cart.dto.request.CartProductModifyRequestDto;
import shop.gaship.gashipfront.cart.dto.response.ProductResponseDto;
import shop.gaship.gashipfront.cart.exception.CartProductAmountException;
import shop.gaship.gashipfront.cart.service.CartService;
import shop.gaship.gashipfront.cart.util.CartUtil;
import shop.gaship.gashipfront.product.adapter.ProductAdapter;
import shop.gaship.gashipfront.product.dto.response.ProductAllInfoResponseDto;



/**
 * {@inheritDoc}
 * CartService 의 구현체입니다.
 */
@Service
public class CartServiceImpl implements CartService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final HashOperations<String, Object, Object> hashOperations;
    private final ProductAdapter productAdapter;

    /**
     * 생성자 주입.
     *
     * @param redisTemplate  redisTemplate
     * @param productAdapter 상품 어뎁터
     */
    @Autowired
    public CartServiceImpl(RedisTemplate<String, Object> redisTemplate,
                           ProductAdapter productAdapter) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
        this.productAdapter = productAdapter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer modifyProductQuantityFromCart(
            String cartNo, CartProductModifyRequestDto request) throws CartProductAmountException {
        redisTemplate.expire(cartNo, 101, TimeUnit.DAYS);
        if (request.getQuantity() > 10 || request.getQuantity() < 1) {
            throw new CartProductAmountException();
        }
        hashOperations.put(cartNo, request.getProductId().toString(), request.getQuantity());
        return request.getQuantity();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteProductFromCart(String cartNo, CartProductDeleteRequestDto request) {
        hashOperations.delete(cartNo, request.getProductId().toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mergeCart(String cartId, Integer memberId) {
        Map<Object, Object> map = hashOperations.entries(cartId);
        String key = String.valueOf(memberId);
        mergeHashMap(key, map);
        hashOperations.delete(cartId);
    }

    /**
     * 키 값이 다른 두개의 레디스를 합치는 메서드 입니다.
     */
    private void mergeHashMap(String key, Map<Object, Object> map) {
        map.forEach((key1, value) -> hashOperations.putIfAbsent(key, key1, value));
    }

    @Override
    public List<ProductResponseDto> getProductsFromCart(String cartId) {
        redisTemplate.expire(cartId, 101, TimeUnit.DAYS);
        Map<Integer, Integer> integerMap = new HashMap<>();
        hashOperations.entries(cartId)
                .forEach((k, v) -> integerMap.put(
                        Integer.parseInt((String.valueOf(k))),
                        Integer.parseInt((String.valueOf(v)))));
        List<ProductAllInfoResponseDto> productList =
                productAdapter.productNosList(new ArrayList<>(integerMap.keySet()));
        return CartUtil.productListToCartList(productList, integerMap);
    }

}

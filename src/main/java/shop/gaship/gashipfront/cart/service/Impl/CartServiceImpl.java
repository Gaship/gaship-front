package shop.gaship.gashipfront.cart.service.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.cart.dto.request.CartProductModifyRequestDto;
import shop.gaship.gashipfront.cart.dto.response.ProductResponseDto;
import shop.gaship.gashipfront.cart.exception.CartMaxLimitException;
import shop.gaship.gashipfront.cart.exception.CartMergeException;
import shop.gaship.gashipfront.cart.exception.CartProductAmountException;
import shop.gaship.gashipfront.cart.service.CartService;
import shop.gaship.gashipfront.cart.util.CartUtil;
import shop.gaship.gashipfront.product.adapter.ProductAdapter;
import shop.gaship.gashipfront.product.dto.response.ProductAllInfoResponseDto;



/**
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
        if (hashOperations.size(cartNo) > (10L)) {
            throw new CartMaxLimitException();
        }
        hashOperations.put(cartNo, request.getProductId().toString(), request.getQuantity());
        return request.getQuantity();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void modifyProductQuantityFromCart(
            String cartNo, Long productNo, Long productQuantity) throws CartProductAmountException {
        redisTemplate.expire(cartNo, 101, TimeUnit.DAYS);
        if (productQuantity > 10 || productQuantity < 1) {
            throw new CartProductAmountException();
        }
        if (hashOperations.size(cartNo) > (10L)) {
            throw new CartMaxLimitException();
        }
        hashOperations.put(cartNo, productNo.toString(), productQuantity.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteProductFromCart(String cartNo, Long id) {
        hashOperations.delete(cartNo, id.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mergeCart(String cartId, Integer memberId) {
        Map<Object, Object> map = hashOperations.entries(cartId);
        String key = String.valueOf(memberId);

        if (hashOperations.size(key) + hashOperations.size(cartId) > (10L)) {
            throw new CartMergeException();
        }
        mergeHashMap(key, map);
        redisTemplate.delete(cartId);
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
        Map<Object, Object> cartInfo = hashOperations.entries(cartId);
        cartInfo.forEach((k, v) -> integerMap.put(
                Integer.parseInt((String.valueOf(k))),
                Integer.parseInt((String.valueOf(v)))));
        List<ProductAllInfoResponseDto> productList =
                productAdapter.productNosList(new ArrayList<>(integerMap.keySet()));
        if (Objects.isNull(productList)){
            return null;
        }
        List<ProductResponseDto> productResponseDtoList = CartUtil.productListToCartList(productList, integerMap);
        // 재고수량이 0이면 장바구니, 레디스에서 삭제하는 로직
        List<ProductResponseDto> stockMoreThanOneDtoList = stockCheckAndDeleteFromCart(cartId, productResponseDtoList);
        // 재고수량이 1이상이고, 장바구니에 담은 상품의 수량이 재고수량보다 많은 경우 강제로 재고수량으로 맞춰준다.
        // 장바구니에서 재고보다 많은 수량을 선택할 경우를 막고, 장바구니 조회에서도 막는다.
        //quantity > 0  && quantity < orderquantity 인 경우 orderquantity = quantity 로 레디스를 변경
        return changeOrderQuantityToMaxStock(cartId, stockMoreThanOneDtoList);
    }

    private List<ProductResponseDto> stockCheckAndDeleteFromCart(String cartId, List<ProductResponseDto> list) {
        //재고가 0인것을 찾고
        List<ProductResponseDto> zeroQuantityProductList = list.stream()
                .filter(ele -> ele.getQuantity() < 1)
                .collect(Collectors.toList());
        // 레디스에서 삭제
        List<Integer> stockZeroList = zeroQuantityProductList.stream().map(ProductResponseDto::getProductNo).collect(Collectors.toList());
        if(stockZeroList.size() > 0){
            stockZeroList.forEach(i -> hashOperations.delete(cartId,i.toString()));
        }
        List<ProductResponseDto> quantityMoreThanOneProductList = list.stream()
                .filter(ele -> ele.getOrderQuantity() >= 1)
                .collect(Collectors.toList());
        return quantityMoreThanOneProductList;
    }

    private List<ProductResponseDto> changeOrderQuantityToMaxStock(String cartId, List<ProductResponseDto> list) {
        List<ProductResponseDto> stockLessThanOrderQuantityList = list.stream()
                .filter(ele -> ele.getOrderQuantity() > ele.getQuantity())
                .collect(Collectors.toList());
        //레디스의 주문수량을 최대 주문수량인 재고수량으로 변경
        stockLessThanOrderQuantityList.forEach(ele -> hashOperations.put(cartId,ele.getProductNo().toString(),ele.getQuantity().toString()));

        List<ProductResponseDto> collect = list.stream()
                .map(ProductResponseDto::changeQuantityToStock)
                .collect(Collectors.toList());
        return collect;
    }


}

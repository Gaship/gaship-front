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
import shop.gaship.gashipfront.cart.exception.CartProductAmountException;
import shop.gaship.gashipfront.cart.exception.ProductStockIsLessThanOrderQuantity;
import shop.gaship.gashipfront.cart.exception.ProductStockIsZeroException;
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
    public void addProductToCart(
            String cartNo, CartProductModifyRequestDto request) throws CartProductAmountException {
        redisTemplate.expire(cartNo, 7, TimeUnit.DAYS);
        productStockMoreThanOneCheck(request.getProductNo());
        orderQuantityAmountCheck(request.getProductNo(),request.getQuantity());
        cartSizeIsMaxCheck(cartNo);
        hashOperations.put(cartNo, request.getProductNo().toString(), request.getQuantity());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void modifyProductQuantityFromCart(
            String cartNo, Integer productNo, Integer productQuantity) throws CartProductAmountException {
        redisTemplate.expire(cartNo, 7, TimeUnit.DAYS);
        productStockMoreThanOneCheck(productNo);
        orderQuantityAmountCheck(productNo, productQuantity);
        cartSizeIsMaxCheck(cartNo);
        hashOperations.put(cartNo, productNo.toString(), productQuantity.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteProductFromCart(String cartNo, Long productNo) {
        hashOperations.delete(cartNo, productNo.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mergeCart(String nonMemberCartNo, String memberNo) {
        Map<Object, Object> nonMemberCartInfo = hashOperations.entries(nonMemberCartNo);
        if (isMergedCartSizeOverLimit(nonMemberCartNo, memberNo)) {
            redisTemplate.delete(nonMemberCartNo);
            return;
        }
        mergeHashMap(memberNo, nonMemberCartInfo);
        redisTemplate.delete(nonMemberCartNo);
    }

    /**
     * 비회원때의 장바구니에 담은 상품 갯수와 회원장바구니상품의 갯수가 장바구니 한계를 넘는지 체크합니다.
     *
     * @param nonMemberCartNo 비회원 장바구니 id
     * @param memberCartNo 회원 장바구니 id
     * @return 장바구니 한계를 넘는지 여부
     */
    private boolean isMergedCartSizeOverLimit(String nonMemberCartNo, String memberCartNo) {
        return hashOperations.size(memberCartNo) + hashOperations.size(nonMemberCartNo) > (10L);
    }

    /**
     * 키 값이 다른 두개의 레디스를 합치는 메서드 입니다.
     */
    private void mergeHashMap(String memberCartNo, Map<Object, Object> map) {
        map.forEach((productNo, quantity) -> hashOperations.putIfAbsent(memberCartNo, productNo, quantity));
    }

    /**
     * 장바구니에 담긴 상품의 종류가 한계를 넘었는지 체크합니다.
     *
     * @param cartNo 회원의 장바구니 id
     */
    private void cartSizeIsMaxCheck(String cartNo) {
        if (hashOperations.size(cartNo) > (10L)) {
            throw new CartMaxLimitException();
        }
    }

    /**
     * 장바구니에 담으려는 상품의 갯수가 적절한지 체크합니다.
     *
     * @param orderQuantity 장바구니에 담으려는 상품의 갯수
     */
    private void orderQuantityAmountCheck(Integer productNo,Integer orderQuantity) throws CartProductAmountException {
        if (orderQuantity > 10 || orderQuantity < 1) {
            throw new CartProductAmountException();
        }
        if (productAdapter.productDetails(productNo).getQuantity() < orderQuantity){
            throw new ProductStockIsLessThanOrderQuantity();
        }
    }

    /**
     * 장바구니에 담으려는 상품의 재고가 남아있는지 체크합니다.
     *
     * @param
     */
    private void productStockMoreThanOneCheck(Integer productNo) {
        if (productAdapter.productDetails(productNo).getQuantity() <= 0) {
            throw new ProductStockIsZeroException();
        }
    }
    
    @Override
    public List<ProductResponseDto> getProductsFromCart(String cartNo) {
        redisTemplate.expire(cartNo, 7, TimeUnit.DAYS);
        Map<Integer, Integer> integerMap = new HashMap<>();
        Map<Object, Object> cartInfo = hashOperations.entries(cartNo);
        cartInfo.forEach((k, v) -> integerMap.put(
                Integer.parseInt((String.valueOf(k))),
                Integer.parseInt((String.valueOf(v)))));
        List<ProductAllInfoResponseDto> productList =
                productAdapter.productNosList(new ArrayList<>(integerMap.keySet()));
        if (Objects.isNull(productList)) {
            return null;
        }
        List<ProductResponseDto> productResponseDtoList = CartUtil.productListToCartList(productList, integerMap);
        // 재고수량이 0이면 장바구니, 레디스에서 삭제하는 로직
        List<ProductResponseDto> stockMoreThanOneDtoList = stockCheckAndDeleteFromCart(cartNo, productResponseDtoList);
        // 재고수량이 1이상이고, 장바구니에 담은 상품의 수량이 재고수량보다 많은 경우 강제로 재고수량으로 맞춰준다.
        // 장바구니에서 재고보다 많은 수량을 선택할 경우를 막고, 장바구니 조회에서도 막는다.
        //quantity > 0  && quantity < orderquantity 인 경우 orderquantity = quantity 로 레디스를 변경
        return changeOrderQuantityToMaxStock(cartNo, stockMoreThanOneDtoList);
    }

    private List<ProductResponseDto> stockCheckAndDeleteFromCart(String cartNo, List<ProductResponseDto> list) {
        //재고가 0인것을 찾고
        List<ProductResponseDto> zeroQuantityProductList = list.stream()
                .filter(ele -> ele.getQuantity() < 1)
                .collect(Collectors.toList());
        // 레디스에서 삭제
        List<Integer> stockZeroList = zeroQuantityProductList.stream().map(ProductResponseDto::getProductNo).collect(Collectors.toList());
        if (stockZeroList.size() > 0) {
            stockZeroList.forEach(i -> hashOperations.delete(cartNo, i.toString()));
        }
        List<ProductResponseDto> quantityMoreThanOneProductList = list.stream()
                .filter(ele -> ele.getOrderQuantity() >= 1)
                .collect(Collectors.toList());
        return quantityMoreThanOneProductList;
    }

    private List<ProductResponseDto> changeOrderQuantityToMaxStock(String cartNo, List<ProductResponseDto> list) {
        List<ProductResponseDto> stockLessThanOrderQuantityList = list.stream()
                .filter(ele -> ele.getOrderQuantity() > ele.getQuantity())
                .collect(Collectors.toList());
        //레디스의 주문수량을 최대 주문수량인 재고수량으로 변경
        stockLessThanOrderQuantityList.forEach(ele -> hashOperations.put(cartNo, ele.getProductNo().toString(), ele.getQuantity().toString()));

        List<ProductResponseDto> collect = list.stream()
                .map(ProductResponseDto::changeQuantityToStock)
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    public void deleteOrderedProductFromCart(String cartNo) {
        redisTemplate.delete(cartNo);
    }
}

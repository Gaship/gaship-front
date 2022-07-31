package shop.gaship.gashipfront.cart.service.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipfront.cart.dummy.CartDummy;
import shop.gaship.gashipfront.cart.exception.IllegalQuantityException;
import shop.gaship.gashipfront.cart.service.CartService;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author 최정우
 * @since 1.0
 */
@ExtendWith(value = SpringExtension.class)
@Import(value = {CartServiceImpl.class})
class CartServiceImplTest {
    private static final String CARTID = "1";
    private static final Integer PRODUCTID = 1;
    private static final Integer CAREPERIOD = 1;
    private static final Integer QUANTITY = 1;
    @MockBean
    RedisTemplate<String, Object> redisTemplate;
    @Mock
    HashOperations<String, Object, Object> hashOperations;
    @Autowired
    private CartService cartService;

    @BeforeEach
    void setUp() {
        when(redisTemplate.opsForHash()).thenReturn(hashOperations);
        ReflectionTestUtils.setField(cartService, "hashOperations", hashOperations);
    }

    @DisplayName("장바구니 상품 추가 메서드 테스트")
    @Test
    void addProductToCartTest() {
        doNothing().when(hashOperations).put(any(), any(), any());

        cartService.addProductToCart(CARTID, CartDummy.CartRequestDtoDummy(PRODUCTID, CAREPERIOD, QUANTITY));

        verify(hashOperations, times(1)).increment(CARTID, PRODUCTID.toString(), QUANTITY);
        verify(hashOperations, times(1)).put(CARTID, PRODUCTID + "-" + CAREPERIOD, QUANTITY);
    }

    @DisplayName("장바구니 상품수량 변경 테스트")
    @Test
    void modifyProductQuantityFromCartTest() throws Exception {
        Integer originQuantity = 4;
        doNothing().when(hashOperations).put(any(), any(), any());
        when(hashOperations.get(anyString(), anyString())).thenReturn(originQuantity);

        cartService.modifyProductQuantityFromCart(CARTID, CartDummy.CartModifyRequestDtoDummy(PRODUCTID, CAREPERIOD, QUANTITY));

        verify(hashOperations, times(1)).increment(CARTID, PRODUCTID.toString(), 1 - originQuantity);
        verify(hashOperations, times(1)).put(CARTID, "1-1", QUANTITY);
    }

    @DisplayName("장바구니 상품 수량 변경 테스트(조회 Fail)")
    @Test
    void modifyProductQuantityFromCartFailTest() {
        Integer originQuantity = 4;
        doNothing().when(hashOperations).put(any(), any(), any());
        when(hashOperations.get(anyString(), anyString())).thenReturn(null);

        assertThatThrownBy(() -> cartService.modifyProductQuantityFromCart(CARTID, CartDummy.CartModifyRequestDtoDummy(PRODUCTID, CAREPERIOD, QUANTITY)))
                .isInstanceOf(IllegalQuantityException.class);

        verify(hashOperations, never()).increment(CARTID, PRODUCTID.toString(), 1 - originQuantity);
        verify(hashOperations, never()).put(CARTID, "1-1", QUANTITY);
    }

    @DisplayName("장바구니 상품 수량 +1 테스트")
    @Test
    void increaseProductQuantityFromCartTest() {
        when(hashOperations.increment(anyString(), anyString(), anyInt())).thenReturn(1L);

        cartService.increaseProductQuantityFromCart(CARTID, CartDummy.CartProductQuantityUpDownRequestDtoDummy(PRODUCTID, CAREPERIOD));

        verify(hashOperations, times(1)).increment(CARTID, PRODUCTID.toString(), 1);
        verify(hashOperations, times(1)).increment(CARTID, "1-1", QUANTITY);
    }

    @DisplayName("장바구니 상품 수량 +1 테스트")
    @Test
    void decreaseProductQuantityFromCartTest() {
        when(hashOperations.increment(anyString(), anyString(), anyInt())).thenReturn(1L);

        cartService.decreaseProductQuantityFromCart(CARTID, CartDummy.CartProductQuantityUpDownRequestDtoDummy(PRODUCTID, CAREPERIOD));

        verify(hashOperations, times(1)).increment(CARTID, PRODUCTID.toString(), -1);
        verify(hashOperations, times(1)).increment(CARTID, "1-1", -1);
    }

    @DisplayName("장바구니 특정 상품 삭제 테스트")
    @Test
    void deleteProductFromCartTest() {
        when(hashOperations.delete(anyString(), anyString(), anyInt())).thenReturn(1L);

        cartService.deleteProductFromCart(CARTID, CartDummy.CartDeleteDtoDummy(PRODUCTID, CAREPERIOD));

        verify(hashOperations, times(1)).increment(CARTID, PRODUCTID.toString(), -1);
        verify(hashOperations, times(1)).delete(CARTID, "1-1");
    }

    @DisplayName("장바구니 조회 테스트")
    @Test
    void getProductsFromCartTest() {
    }
}
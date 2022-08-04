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
import shop.gaship.gashipfront.cart.exception.CartProductAmountException;
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
    @MockBean
    RedisTemplate<String, Object> redisTemplate;

    @Mock
    HashOperations<String, Object, Object> hashOperations;

    @Autowired
    private CartService cartService;

    private static final String CARTID = "1";
    private static final Integer PRODUCTID = 1;
    private static final Integer CAREPERIOD = 1;
    private static final Integer QUANTITY = 1;

    @BeforeEach
    void setUp() {
        when(redisTemplate.opsForHash()).thenReturn(hashOperations);
        ReflectionTestUtils.setField(cartService, "hashOperations", hashOperations);
    }

    @DisplayName("장바구니 상품 추가 메서드 테스트")
    @Test
    void addProductToCartTest() {
        when(hashOperations.increment(CARTID, PRODUCTID+"-"+CAREPERIOD, QUANTITY)).thenReturn(Long.valueOf(QUANTITY));

        cartService.addProductToCart(CARTID, CartDummy.CartRequestDtoDummy(PRODUCTID, CAREPERIOD, QUANTITY));

        verify(hashOperations, times(1)).increment(CARTID, PRODUCTID + "-" + CAREPERIOD, QUANTITY);
    }

    @DisplayName("장바구니 상품수량 변경 테스트")
    @Test
    void modifyProductQuantityFromCartTest() throws Exception {
        doNothing().when(hashOperations).put(any(), any(), any());

        cartService.modifyProductQuantityFromCart(CARTID, CartDummy.CartModifyRequestDtoDummy(PRODUCTID, CAREPERIOD, QUANTITY));

        verify(hashOperations, times(1)).put(CARTID, PRODUCTID+"-"+CAREPERIOD, QUANTITY);
    }

    @DisplayName("장바구니 상품 수량 +1 테스트")
    @Test
    void increaseProductQuantityFromCartTest() {
        when(hashOperations.increment(anyString(), anyString(), anyInt())).thenReturn(1L);

        cartService.increaseProductQuantityFromCart(CARTID, CartDummy.CartProductQuantityUpDownRequestDtoDummy(PRODUCTID, CAREPERIOD));

        verify(hashOperations, times(1)).increment(CARTID, PRODUCTID+"-"+CAREPERIOD, QUANTITY);
    }

    @DisplayName("장바구니 상품 수량 -1 테스트")
    @Test
    void decreaseProductQuantityFromCartTest() throws Exception {
        when(hashOperations.increment(anyString(), anyString(), anyInt())).thenReturn(1L);
        when(hashOperations.get(anyString(), anyString())).thenReturn(3);

        cartService.decreaseProductQuantityFromCart(CARTID, CartDummy.CartProductQuantityUpDownRequestDtoDummy(PRODUCTID, CAREPERIOD));

        verify(hashOperations, times(1)).increment(CARTID, PRODUCTID+"-"+CAREPERIOD, -1);
    }

    @DisplayName("장바구니 상품 수량 -1 테스트(수량을 빼려했더니 1 이하의 숫자임(Fail))")
    @Test
    void decreaseProductQuantityFromCartFailTest() throws Exception {
        when(hashOperations.increment(anyString(), anyString(), anyInt())).thenReturn(1L);
        when(hashOperations.get(anyString(), anyString())).thenReturn(1);

        assertThatThrownBy(() -> cartService.decreaseProductQuantityFromCart(CARTID, CartDummy.CartProductQuantityUpDownRequestDtoDummy(PRODUCTID, CAREPERIOD)))
                .isInstanceOf(CartProductAmountException.class);

        verify(hashOperations, never()).increment(CARTID, PRODUCTID+"-"+CAREPERIOD, -1);
    }

    @DisplayName("장바구니 특정 상품 삭제 테스트")
    @Test
    void deleteProductFromCartTest1() throws Exception {
        when(hashOperations.increment(CARTID, PRODUCTID + "-" + CAREPERIOD, -1)).thenReturn(-1L);
        when(hashOperations.get(CARTID, PRODUCTID + "-" + CAREPERIOD)).thenReturn(100);
        when(hashOperations.delete(CARTID, PRODUCTID + "-" + CAREPERIOD)).thenReturn(1L);

        cartService.deleteProductFromCart(CARTID, CartDummy.CartDeleteDtoDummy(PRODUCTID, CAREPERIOD));

        verify(hashOperations, times(1)).increment(CARTID, PRODUCTID+"-"+CAREPERIOD, -1);
        verify(hashOperations, times(1)).get(CARTID, PRODUCTID + "-" + CAREPERIOD);
        verify(hashOperations, never()).delete(CARTID, PRODUCTID + "-" + CAREPERIOD);
    }

    @DisplayName("장바구니 특정 상품 삭제 테스트, 상품이 레디스에서 삭제")
    @Test
    void deleteProductFromCartTest2() throws Exception {
        when(hashOperations.increment(CARTID, PRODUCTID + "-" + CAREPERIOD, -1)).thenReturn(-1L);
        when(hashOperations.get(CARTID, PRODUCTID + "-" + CAREPERIOD)).thenReturn(0);
        when(hashOperations.delete(CARTID, PRODUCTID + "-" + CAREPERIOD)).thenReturn(1L);

        cartService.deleteProductFromCart(CARTID, CartDummy.CartDeleteDtoDummy(PRODUCTID, CAREPERIOD));

        verify(hashOperations, times(1)).increment(CARTID, PRODUCTID+"-"+CAREPERIOD, -1);
        verify(hashOperations, times(1)).get(CARTID, PRODUCTID + "-" + CAREPERIOD);
        verify(hashOperations, times(1)).delete(CARTID, PRODUCTID + "-" + CAREPERIOD);
    }

    @DisplayName("장바구니 특정 상품 삭제 테스트 (해당 상품을 키값으로 조회했지만 null(Fail))")
    @Test
    void deleteProductFromCartTest4() throws Exception {
        when(hashOperations.increment(CARTID, PRODUCTID + "-" + CAREPERIOD, -1)).thenReturn(-1L);
        when(hashOperations.get(CARTID, PRODUCTID + "-" + CAREPERIOD)).thenReturn(null);
        when(hashOperations.delete(CARTID, PRODUCTID + "-" + CAREPERIOD)).thenReturn(1L);

        assertThatThrownBy(()-> cartService.deleteProductFromCart(CARTID, CartDummy.CartDeleteDtoDummy(PRODUCTID, CAREPERIOD)))
                .isInstanceOf(IllegalQuantityException.class);

        verify(hashOperations, times(1)).increment(CARTID, PRODUCTID+"-"+CAREPERIOD, -1);
        verify(hashOperations, times(1)).get(CARTID, PRODUCTID + "-" + CAREPERIOD);
        verify(hashOperations, never()).delete(CARTID, PRODUCTID + "-" + CAREPERIOD);
    }

    @DisplayName("장바구니 조회 테스트")
    @Test
    void getProductsFromCartTest() {
    }
}
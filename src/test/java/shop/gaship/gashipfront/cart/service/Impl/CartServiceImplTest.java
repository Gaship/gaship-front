package shop.gaship.gashipfront.cart.service.Impl;

import org.junit.jupiter.api.BeforeEach;
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
import shop.gaship.gashipfront.cart.service.CartService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author 최정우
 * @since 1.0
 */
@ExtendWith(value = SpringExtension.class)
@Import(value = {CartServiceImpl.class})
class CartServiceImplTest {
    @Autowired
    private CartService cartService;

    @MockBean
    RedisTemplate<String, Object> redisTemplate;

    @Mock
    HashOperations<String, Object, Object> hashOperations;

    @BeforeEach
    void setUp(){
        when(redisTemplate.opsForHash()).thenReturn(hashOperations);
        ReflectionTestUtils.setField(cartService, "hashOperations", hashOperations);
    }
    @Test
    void addProductToCart() {
        doNothing().when(hashOperations).put(any(), any(), any());

        cartService.addProductToCart(CartDummy.CartRequestDtoDummy("1", 1, 1, 1));

        verify(hashOperations, times(1)).put("1", "1-1", 1);
    }

    @Test
    void modifyProductQuantityFromCart() {
        doNothing().when(hashOperations).put(any(), any(), any());

        cartService.modifyProductQuantityFromCart(CartDummy.CartModifyRequestDtoDummy("1", 1, 1, 1));

        verify(hashOperations, times(1)).put("1", "1-1", 1);
    }

    @Test
    void increaseProductQuantityFromCart() {
        when(hashOperations.increment(anyString(), anyString(), anyInt())).thenReturn(1L);

        cartService.increaseProductQuantityFromCart(CartDummy.CartProductQuantityUpDownRequestDtoDummy("1", 1, 1));

        verify(hashOperations, times(1)).increment("1", "1-1", 1);
    }

    @Test
    void decreaseProductQuantityFromCart() {
        when(hashOperations.increment(anyString(), anyString(), anyInt())).thenReturn(1L);

        cartService.decreaseProductQuantityFromCart(CartDummy.CartProductQuantityUpDownRequestDtoDummy("1", 1, 1));

        verify(hashOperations, times(1)).increment("1", "1-1", -1);
    }

    @Test
    void deleteProductFromCart() {
        when(hashOperations.delete(anyString(), anyString(), anyInt())).thenReturn(1L);

        cartService.deleteProductFromCart(CartDummy.CartDeleteDtoDummy("1", 1, 1));

        verify(hashOperations, times(1)).delete("1", "1-1");
    }

    @Test
    void getProductsFromCart() {
    }
}
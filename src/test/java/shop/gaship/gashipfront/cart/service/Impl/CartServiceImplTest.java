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

    @BeforeEach
    void setUp() {
        when(redisTemplate.opsForHash()).thenReturn(hashOperations);
        ReflectionTestUtils.setField(cartService, "hashOperations", hashOperations);
    }

    @DisplayName("장바구니 상품수량 변경 테스트")
    @Test
    void modifyProductQuantityFromCartTest() throws Exception {
        doNothing().when(hashOperations).put(any(), any(), any());

        cartService.modifyProductQuantityFromCart("1", CartDummy.cartProductModifyRequestDto(1, 1));

        verify(hashOperations, times(1)).put("1", 1, 1);
    }

    @DisplayName("장바구니 상품수량 변경 테스트(수량이 1 개 미만(Fail))")
    @Test
    void modifyProductQuantityFromCartTestFail() throws CartProductAmountException {
        doNothing().when(hashOperations).put(any(), any(), any());

        assertThatThrownBy(()-> cartService.modifyProductQuantityFromCart("1", CartDummy.cartProductModifyRequestDto(1, 11)))
                .isInstanceOf(CartProductAmountException.class);

        verify(hashOperations, never()).put("1", 1, 1);
    }

    @DisplayName("장바구니 상품수량 변경 테스트(수량이 1 개 미만(Fail))")
    @Test
    void modifyProductQuantityFromCartTestFail2() {
        doNothing().when(hashOperations).put(any(), any(), any());

        assertThatThrownBy(()-> cartService.modifyProductQuantityFromCart("1", CartDummy.cartProductModifyRequestDto(1, 0)))
                .isInstanceOf(CartProductAmountException.class);

        verify(hashOperations, never()).put("1", 1, 1);
    }

    @DisplayName("장바구니 특정 상품 삭제 테스트")
    @Test
    void deleteProductFromCartTest1() throws Exception {
        when(hashOperations.delete("1", 1)).thenReturn(1L);

        cartService.deleteProductFromCart("1", CartDummy.cartProductDeleteRequestDto(1));

        verify(hashOperations, times(1)).delete("1", 1);
    }

    @DisplayName("장바구니 조회 테스트")
    @Test
    void getProductsFromCartTest() {
    }


}
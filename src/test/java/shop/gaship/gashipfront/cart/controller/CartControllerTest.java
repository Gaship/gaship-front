package shop.gaship.gashipfront.cart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shop.gaship.gashipfront.cart.dummy.CartDummy;
import shop.gaship.gashipfront.cart.service.CartService;
import shop.gaship.gashipfront.config.SecurityConfig;
import shop.gaship.gashipfront.config.SecurityEmployeeConfig;

import javax.servlet.http.Cookie;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author 최정우
 * @since 1.0
 */
@WebMvcTest(controllers = CartController.class,
        excludeAutoConfiguration = {
                SecurityAutoConfiguration.class,
                OAuth2ClientAutoConfiguration.class,
                ThymeleafAutoConfiguration.class

        },
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityEmployeeConfig.class)
        })
class CartControllerTest {
    private static final String CART_ID = "CID";
    @MockBean
    CartService cartService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @DisplayName("쿠키가 없는 방문자가 물건 상세페이지에서 수량을 선택한 후 장바구니에 담기 버튼을 클릭했을 때")
    @Test
    void addToCartTest1() throws Exception {
        when(cartService.modifyProductQuantityFromCart(any(), any())).thenReturn(11);
        String body = objectMapper.writeValueAsString(CartDummy.cartProductModifyRequestDto(1, 11));

        mockMvc.perform(post("/carts/add-product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(cookie().exists(CART_ID));


        verify(cartService, times(1)).modifyProductQuantityFromCart(anyString(), any());
    }

    @DisplayName("쿠키가 있는 비회원이 물건 상세페이지에서 수량을 선택한 후 장바구니에 담기 버튼을 클릭했을 때")
    @Test
    void addToCartTest2() throws Exception {
        when(cartService.modifyProductQuantityFromCart(any(), any())).thenReturn(1);
        String body = objectMapper.writeValueAsString(CartDummy.cartProductModifyRequestDto(1, 1));

        mockMvc.perform(post("/carts/add-product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                        .cookie(new Cookie(CART_ID, UUID.randomUUID().toString()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(cookie().doesNotExist(CART_ID));

        verify(cartService, times(1)).modifyProductQuantityFromCart(anyString(), any());
    }

    @DisplayName("쿠키가 있는 회원이 물건 상세페이지에서 수량을 선택한 후 장바구니에 담기 버튼을 클릭했을 때")
    @Test
    void addToCartTest3() throws Exception {
        when(cartService.modifyProductQuantityFromCart(any(), any())).thenReturn(1);
        String body = objectMapper.writeValueAsString(CartDummy.cartProductModifyRequestDto(1, 1));

        mockMvc.perform(post("/carts/add-product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                        .cookie(new Cookie(CART_ID, "3"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(cookie().doesNotExist(CART_ID));

        verify(cartService, times(1)).modifyProductQuantityFromCart(anyString(), any());
    }

    @DisplayName("비회원이 장바구니에서 상품에 수량 변경")
    @Test
    void modifyFromCartTest1() throws Exception {
        when(cartService.modifyProductQuantityFromCart(any(), any())).thenReturn(1);
        String body = objectMapper.writeValueAsString(CartDummy.cartProductModifyRequestDto(3, 21));

        mockMvc.perform(put("/carts/modify-quantity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                        .cookie(new Cookie(CART_ID, UUID.randomUUID().toString()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(cartService, times(1)).modifyProductQuantityFromCart(anyString(), any());
    }

    @DisplayName("회원이 장바구니에서 상품에 수량 변경")
    @Test
    void modifyFromCartTes2t() throws Exception {
        when(cartService.modifyProductQuantityFromCart(any(), any())).thenReturn(7);
        String body = objectMapper.writeValueAsString(CartDummy.cartProductModifyRequestDto(3, 7));

        mockMvc.perform(put("/carts/modify-quantity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                        .cookie(new Cookie(CART_ID, "3"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(cartService, times(1)).modifyProductQuantityFromCart(anyString(), any());
    }

    @DisplayName("비회원이 장바구니에서 상품을 삭제할 때")
    @Test
    void deleteFromCartTest1() throws Exception {
        doNothing().when(cartService).deleteProductFromCart(any(), any());
        String body = objectMapper.writeValueAsString(CartDummy.cartProductDeleteRequestDto(1));

        mockMvc.perform(delete("/carts/delete-product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                        .cookie(new Cookie(CART_ID, UUID.randomUUID().toString()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(cartService, times(1)).deleteProductFromCart(anyString(), any());
    }

    @DisplayName("회원이 장바구니에서 상품을 삭제할 때")
    @Test
    void deleteFromCartTest2() throws Exception {
        doNothing().when(cartService).deleteProductFromCart(any(), any());
        String body = objectMapper.writeValueAsString(CartDummy.cartProductDeleteRequestDto(1));

        mockMvc.perform(delete("/carts/delete-product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                        .cookie(new Cookie(CART_ID, "3"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(cartService, times(1)).deleteProductFromCart(anyString(), any());
    }

    @DisplayName("쿠키가 있는 방문자가 상품목록을 조회")
    @Test
    void getProductsFromCartTest() throws Exception {
        when(cartService.getProductsFromCart(any())).thenReturn(null);

        mockMvc.perform(get("/carts/")
                        .cookie(new Cookie(CART_ID, "3"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(cartService, times(1)).getProductsFromCart(anyString());
    }

    @DisplayName("쿠키가 없는 방문자가 상품목록을 조회")
    @Test
    void getProductsFromCartTest2() throws Exception {
        when(cartService.getProductsFromCart(any())).thenReturn(null);

        mockMvc.perform(get("/carts/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(cartService, never()).getProductsFromCart(anyString());
    }
}
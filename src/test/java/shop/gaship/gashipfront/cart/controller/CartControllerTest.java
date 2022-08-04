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
import shop.gaship.gashipfront.cart.dto.request.CartDeleteRequestDto;
import shop.gaship.gashipfront.cart.dto.request.CartProductQuantityUpDownRequestDto;
import shop.gaship.gashipfront.cart.dto.request.CartRequestDto;
import shop.gaship.gashipfront.cart.dummy.CartDummy;
import shop.gaship.gashipfront.cart.service.CartService;
import shop.gaship.gashipfront.config.SecurityConfig;
import shop.gaship.gashipfront.config.SecurityEmployeeConfig;

import javax.servlet.http.Cookie;
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
    @MockBean
    CartService cartService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private static final String NON_MEMBER_CART_ID = "NONMEMBERCARTID";
    private static final String MEMBER_CART_ID = "MEMBERCARTID";
    
    @DisplayName("쿠키가 없는 방문자가 물건 상세페이지에서 수량을 선택한 후 장바구니에 담기 버튼을 클릭했을 때")
    @Test
    void addToCartTest1() throws Exception {
        doNothing().when(cartService).addProductToCart(any(), any());
        String body = objectMapper.writeValueAsString(CartRequestDto.builder()
                .productId(15)
                .carePeriod(3)
                .quantity(2)
                .build());

        mockMvc.perform(post("/carts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(cookie().exists(NON_MEMBER_CART_ID));


        verify(cartService, times(1)).addProductToCart(anyString(), any(CartRequestDto.class));
    }

    @DisplayName("쿠키가 있는 비회원이 물건 상세페이지에서 수량을 선택한 후 장바구니에 담기 버튼을 클릭했을 때")
    @Test
    void addToCartTest2() throws Exception {
        doNothing().when(cartService).addProductToCart(any(), any());
        String body = objectMapper.writeValueAsString(CartRequestDto.builder()
                .productId(15)
                .carePeriod(3)
                .quantity(2)
                .build());

        mockMvc.perform(post("/carts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                        .cookie(new Cookie(NON_MEMBER_CART_ID, UUID.randomUUID().toString()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(cookie().doesNotExist(NON_MEMBER_CART_ID));

        verify(cartService, times(1)).addProductToCart(anyString(), any(CartRequestDto.class));
    }

    @DisplayName("쿠키가 있는 회원이 물건 상세페이지에서 수량을 선택한 후 장바구니에 담기 버튼을 클릭했을 때")
    @Test
    void addToCartTest3() throws Exception {
        doNothing().when(cartService).addProductToCart(any(), any());
        String body = objectMapper.writeValueAsString(CartRequestDto.builder()
                .productId(15)
                .carePeriod(3)
                .quantity(2)
                .build());

        mockMvc.perform(post("/carts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                        .cookie(new Cookie(MEMBER_CART_ID, "3"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(cookie().doesNotExist(NON_MEMBER_CART_ID));

        verify(cartService, times(1)).addProductToCart(anyString(), any(CartRequestDto.class));
    }

    @DisplayName("비회원이 장바구니에서 상품에 수량 변경")
    @Test
    void modifyFromCartTest1() throws Exception {
        doNothing().when(cartService).modifyProductQuantityFromCart(any(), any());
        String body = objectMapper.writeValueAsString(CartDummy.CartModifyRequestDtoDummy(15, 3, 21));

        mockMvc.perform(put("/carts/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                        .cookie(new Cookie(NON_MEMBER_CART_ID, UUID.randomUUID().toString()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(cartService, times(1)).modifyProductQuantityFromCart(anyString(), any());
    }

    @DisplayName("회원이 장바구니에서 상품에 수량 변경")
    @Test
    void modifyFromCartTes2t() throws Exception {
        doNothing().when(cartService).modifyProductQuantityFromCart(any(), any());
        String body = objectMapper.writeValueAsString(CartDummy.CartModifyRequestDtoDummy(15, 3, 21));

        mockMvc.perform(put("/carts/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                        .cookie(new Cookie(MEMBER_CART_ID, "3"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(cartService, times(1)).modifyProductQuantityFromCart(anyString(), any());
    }


    @DisplayName("비회원이 장바구니에서 상품에서 +1 을 눌렀을 때")
    @Test
    void increaseProductQuantityFromCartTest1() throws Exception {
        doNothing().when(cartService).increaseProductQuantityFromCart(any(), any());
        String body = objectMapper.writeValueAsString
                (CartProductQuantityUpDownRequestDto.builder()
                        .productId(15)
                        .carePeriod(3)
                        .build());

        mockMvc.perform(put("/carts/products/increase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                        .cookie(new Cookie(NON_MEMBER_CART_ID, UUID.randomUUID().toString()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(cartService, times(1)).increaseProductQuantityFromCart(anyString(), any());
    }

    @DisplayName("회원이 장바구니에서 상품에서 +1 을 눌렀을 때")
    @Test
    void increaseProductQuantityFromCartTest2() throws Exception {
        doNothing().when(cartService).increaseProductQuantityFromCart(any(), any());
        String body = objectMapper.writeValueAsString
                (CartProductQuantityUpDownRequestDto.builder()
                        .productId(15)
                        .carePeriod(3)
                        .build());

        mockMvc.perform(put("/carts/products/increase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                        .cookie(new Cookie(MEMBER_CART_ID, "3"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(cartService, times(1)).increaseProductQuantityFromCart(anyString(), any());
    }

    @DisplayName("비회원이 장바구니에서 상품에서 -1 을 눌렀을 때")
    @Test
    void decreaseProductQuantityFromCartTest1() throws Exception {
        doNothing().when(cartService).decreaseProductQuantityFromCart(any(), any());
        String body = objectMapper.writeValueAsString
                (CartProductQuantityUpDownRequestDto.builder()
                        .productId(15)
                        .carePeriod(3)
                        .build());

        mockMvc.perform(put("/carts/products/decrease")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                        .cookie(new Cookie(NON_MEMBER_CART_ID, UUID.randomUUID().toString()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(cartService, times(1)).decreaseProductQuantityFromCart(anyString(), any());
    }

    @DisplayName("회원이 장바구니에서 상품에서 -1 을 눌렀을 때")
    @Test
    void decreaseProductQuantityFromCartTest2() throws Exception {
        doNothing().when(cartService).decreaseProductQuantityFromCart(any(), any());
        String body = objectMapper.writeValueAsString
                (CartProductQuantityUpDownRequestDto.builder()
                        .productId(15)
                        .carePeriod(3)
                        .build());

        mockMvc.perform(put("/carts/products/decrease")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                        .cookie(new Cookie(MEMBER_CART_ID,"3"))
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk());

        verify(cartService, times(1)).decreaseProductQuantityFromCart(anyString(), any());
    }

    @DisplayName("비회원이 장바구니에서 상품을 삭제할 때")
    @Test
    void deleteFromCartTest1() throws Exception {
        doNothing().when(cartService).deleteProductFromCart(any(), any());
        String body = objectMapper.writeValueAsString
                (CartDeleteRequestDto.builder()
                        .productId(15)
                        .carePeriod(3)
                        .build());

        mockMvc.perform(delete("/carts/products/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                        .cookie(new Cookie(NON_MEMBER_CART_ID, UUID.randomUUID().toString()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(cartService, times(1)).deleteProductFromCart(anyString(), any());
    }

    @DisplayName("회원이 장바구니에서 상품을 삭제할 때")
    @Test
    void deleteFromCartTest2() throws Exception {
        doNothing().when(cartService).deleteProductFromCart(any(), any());
        String body = objectMapper.writeValueAsString
                (CartDeleteRequestDto.builder()
                        .productId(15)
                        .carePeriod(3)
                        .build());

        mockMvc.perform(delete("/carts/products/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                        .cookie(new Cookie(MEMBER_CART_ID, "3"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(cartService, times(1)).deleteProductFromCart(anyString(), any());
    }

    @Test
    void getProductsFromCartTest() {
    }

}
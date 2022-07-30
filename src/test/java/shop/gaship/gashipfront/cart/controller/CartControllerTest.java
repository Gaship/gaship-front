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
import org.springframework.test.web.servlet.MockMvc;
import shop.gaship.gashipfront.cart.service.CartService;
import shop.gaship.gashipfront.config.SecurityConfig;
//import shop.gaship.gashipfront.config.SecurityEmployeeConfig;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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
//                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityEmployeeConfig.class)
        })
class CartControllerTest {
    @MockBean
    CartService cartService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @DisplayName("회원이 물건 상세페이지에서 수량을 선택한 후 장바구니에 담기 버튼을 클릭했을 때")
    @Test
    void CartAddTest() throws Exception {
//        doNothing().when(cartService).addToCart(any());
//
//        String body = objectMapper.writeValueAsString(CartAddRequestDto.builder().cartId("1").productId("1").carePeriod("10").quantity("1").build());
//
//        mockMvc.perform(post("/carts")
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(body)
//                    .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated());
//
//        verify(cartService,times(1)).addToCart(any());
    }
}
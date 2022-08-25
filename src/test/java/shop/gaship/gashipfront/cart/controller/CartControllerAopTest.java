//package shop.gaship.gashipfront.cart.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.junit.jupiter.api.Test;
//import org.springframework.aop.support.AopUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.MediaType;
//import org.springframework.test.util.ReflectionTestUtils;
//import org.springframework.test.web.servlet.MockMvc;
//import shop.gaship.gashipfront.aspect.CartIdCheckAspect;
//import shop.gaship.gashipfront.cart.dto.request.CartProductModifyRequestDto;
//import shop.gaship.gashipfront.cart.service.CartService;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//
///**
// * @author 최정우
// * @since 1.0
// */
//@RequiredArgsConstructor
//@Slf4j
//@WebMvcTest(CartController.class)
//@Import(CartIdCheckAspect.class)
//class CartControllerAopTest {
//    @MockBean
//    CartService cartService;
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @Autowired
//    MockMvc mockMvc;
//
//    @Test
//    void aopTest2(){
//        CartProductModifyRequestDto cartProductModifyRequestDto = new CartProductModifyRequestDto();
//        ReflectionTestUtils.setField(cartProductModifyRequestDto,"productId",3);
//        ReflectionTestUtils.setField(cartProductModifyRequestDto,"quantity",3);
//
//        String body = objectMapper.writeValueAsString(cartProductModifyRequestDto);
//        mockMvc.perform(post("/carts/add-product")
//                .content(body)
//                .contentType(MediaType.APPLICATION_JSON)
//                        .)
//                .andExpect()
//
//    }
//}

package shop.gaship.gashipfront.security.social.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import javax.swing.Spring;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.client.WebClientRestTemplateAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import shop.gaship.gashipfront.config.SecurityConfig;
import shop.gaship.gashipfront.security.basic.service.CustomUserDetailService;
import shop.gaship.gashipfront.security.social.dto.accesstoken.NaverAccessToken;
import shop.gaship.gashipfront.security.social.dto.domain.Member;
import shop.gaship.gashipfront.security.social.dto.userdata.NaverUserData;
import shop.gaship.gashipfront.security.social.dto.userdata.NaverUserDataResponse;
import shop.gaship.gashipfront.security.social.service.common.ShoppingmallService;
import shop.gaship.gashipfront.security.social.service.dance.NaverLoginService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * packageName    : shop.gaship.gashipfront.security.social.controller
 * fileName       : OAuthControllerTest
 * author         : choi-gyeom-jun
 * date           : 2022-07-14
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-07-14        choi-gyeom-jun       최초 생성
 */
@WebMvcTest(
    controllers = OAuthController.class,
    excludeAutoConfiguration = {
        SecurityAutoConfiguration.class,
        OAuth2ClientAutoConfiguration.class,
        ThymeleafAutoConfiguration.class
    },
    excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
    }
)

//@WebMvcTest(OAuthController.class)
//@ExtendWith(SpringExtension.class)
//@Import(CustomUserDetailService.class)
class OAuthControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private NaverLoginService naverLoginService;

    @MockBean
    private ShoppingmallService shoppingmallService;

    @DisplayName("지정한 uri를 redirectUri로 잘맞게 response된다.")
    @Test
    void redirectUriForLoginPageRequestNaver() throws Exception {
        // given
        String redirectUri = "abc@naver.com";
        given(naverLoginService.getUriForLoginPageRequest(any()))
            .willReturn(redirectUri);

        // when
        mvc.perform(get("/securities/login/naver"))
            .andExpect(status().is(302))
            .andExpect(redirectedUrl(redirectUri));
    }

    @DisplayName("서비스가 잘동작했을때 200상태코드와 /all이라는 view name이 response객체에 들어간다.")
    @Test
    void getAccessTokenAndAuthenticateNaver() throws Exception {
        // given
        NaverAccessToken token = new NaverAccessToken();
        token.setAccessToken("this is accesstoken");

        NaverUserData data = new NaverUserData();
        NaverUserDataResponse response = new NaverUserDataResponse();
        response.setEmail("abc@naver.com");
        data.setResponse(response);

        Member member = new Member();
        member.setEmail("abc@naver.com");
        member.setPassword("1234");

        List<String> authorities = new ArrayList<>();
        authorities.add("USER");
        member.setAuthorities(authorities);

        given(naverLoginService.getAccessToken(any(), any(), any()))
            .willReturn(token);

        given(naverLoginService.getUserDataThroughAccessToken(anyString()))
            .willReturn(data);

        given(shoppingmallService.getMember(anyString()))
            .willReturn(member);

        // when then
        MvcResult result = mvc.perform(get("/securities/login/naver/callback"))
            .andExpect(status().isOk())
            .andExpect(view().name("/all"))
            .andReturn();
    }
}
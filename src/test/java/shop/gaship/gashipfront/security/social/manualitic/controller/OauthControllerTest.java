package shop.gaship.gashipfront.security.social.manualitic.controller;

import java.util.ArrayList;
import java.util.List;
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
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import shop.gaship.gashipfront.cart.service.CartService;
import shop.gaship.gashipfront.config.SecurityConfig;
import shop.gaship.gashipfront.config.SecurityEmployeeConfig;
import shop.gaship.gashipfront.config.ServerConfig;
import shop.gaship.gashipfront.security.basic.service.CustomUserDetailService;
import shop.gaship.gashipfront.security.common.gashipauth.service.AuthApiService;
import shop.gaship.gashipfront.security.common.util.SignupManager;
import shop.gaship.gashipfront.security.social.manualitic.dto.NaverAccessToken;
import shop.gaship.gashipfront.security.social.manualitic.dto.userdata.NaverUserData;
import shop.gaship.gashipfront.security.social.manualitic.dto.userdata.NaverUserDataResponse;
import shop.gaship.gashipfront.security.common.dto.JwtDto;
import shop.gaship.gashipfront.security.social.manualitic.service.NaverLoginService;
import shop.gaship.gashipfront.member.dto.MemberAllFieldDto;

import javax.servlet.http.Cookie;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
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
    controllers = OauthController.class,
    excludeAutoConfiguration = {
        SecurityAutoConfiguration.class,
        OAuth2ClientAutoConfiguration.class,
        ThymeleafAutoConfiguration.class

    },
    excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityEmployeeConfig.class)
    }
)
@Import({ServerConfig.class, CustomUserDetailService.class})
class OauthControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private NaverLoginService naverLoginService;

    @MockBean
    private AuthApiService authApiService;

    @MockBean
    private SignupManager signupManager;

    @MockBean
    private CartService cartService;

    @DisplayName("지정한 uri를 redirectUri로 잘맞게 response된다.")
    @Test
    void redirectUriForLoginPageRequestNaver() throws Exception {
        // given
        String redirectUri = "abc@naver.com?redirect=123&state=1234";
        given(naverLoginService.getUriForLoginPageRequest())
            .willReturn(redirectUri);

        // when
        mvc.perform(get("/securities/login/naver"))
            .andExpect(status().is(302))
            .andExpect(redirectedUrl(redirectUri));
    }

    @DisplayName("auth 서버에서 문제없이 통신이되었을때 302상태코드와 session에 jwt토큰이 잘 저장된다.")
    @Test
    void getAccessTokenAndAuthenticateNaver() throws Exception {
        // given
        givingNaverAccessToken();
        givingNaverUserData();
        givingMember();
        JwtDto jwt = givingJwt();
        Cookie cookie1 = new Cookie("CID", "123");

        // when then
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("state", "1234");

        mvc.perform(get("/securities/login/naver/callback").session(session)
                .param("code", "1234")
                .param("state", "1234")
                .cookie(cookie1))
            .andExpect(status().is(302));

        JwtDto jwtResult = (JwtDto) session.getAttribute("jwt");
        String accessToken = jwtResult.getAccessToken();
        String refreshToken = jwtResult.getRefreshToken();
        assertThat(accessToken)
            .isEqualTo(jwt.getAccessToken());
        assertThat(refreshToken)
            .isEqualTo(jwt.getRefreshToken());

        session.invalidate();
    }


    private void givingNaverAccessToken() {
        NaverAccessToken oauthToken = new NaverAccessToken();
        oauthToken.setAccessToken("this is accesstoken");

        given(naverLoginService.getAccessToken(any(), any(), any()))
            .willReturn(oauthToken);
    }

    private void givingNaverUserData() {
        NaverUserData data = mock(NaverUserData.class);

        NaverUserDataResponse response = new NaverUserDataResponse();
        response.setEmail("abc@naver.com");
        response.setName("홍길동");
        response.setBirthyear("2020");
        response.setBirthday("02-20");
        response.setPhoneNumber("01012341234");

        given(naverLoginService.getUserDataThroughAccessToken(anyString()))
            .willReturn(data);
        when(data.getResponse())
            .thenReturn(response);
    }

    private void givingMember() {
        MemberAllFieldDto member = new MemberAllFieldDto();
        member.setEmail("abc@naver.com");
        member.setPassword("1234");
        member.setMemberNo(123412);

        List<String> authorities = new ArrayList<>();
        authorities.add("USER");
        member.setAuthorities(authorities);

        given(signupManager.getMember(any(NaverUserDataResponse.class)))
            .willReturn(member);
    }

    private JwtDto givingJwt() throws Exception {
        JwtDto jwt = new JwtDto();
        jwt.setAccessToken("this is access!!");
        jwt.setRefreshToken("this is refresh!!");
        given(authApiService.getJwt(any(), any()))
            .willReturn(jwt);
        return jwt;
    }
}
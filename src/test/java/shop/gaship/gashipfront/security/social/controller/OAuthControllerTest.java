package shop.gaship.gashipfront.security.social.controller;

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
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import shop.gaship.gashipfront.config.SecurityConfig;
import shop.gaship.gashipfront.security.social.dto.accesstoken.NaverAccessToken;
import shop.gaship.gashipfront.security.social.dto.domain.Member;
import shop.gaship.gashipfront.security.social.dto.jwt.JwtTokenDto;
import shop.gaship.gashipfront.security.social.dto.userdata.NaverUserData;
import shop.gaship.gashipfront.security.social.dto.userdata.NaverUserDataResponse;
import shop.gaship.gashipfront.security.social.service.common.CommonService;
import shop.gaship.gashipfront.security.social.service.dance.NaverLoginService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
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
    private CommonService commonService;

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

    @DisplayName("auth 서버에서 문제없이 통신이되었을때 200상태코드와 /all이라는 view name이 response객체에 들어가며 session에 jwt토큰이 잘 저장된다.")
    @Test
    void getAccessTokenAndAuthenticateNaver() throws Exception {
        // given
        givingNaverAccessToken();
        givingNaverUserData();
        givingMember();
        JwtTokenDto jwt = givingJwt();

        // when then
        MockHttpSession session = new MockHttpSession();
        mvc.perform(get("/securities/login/naver/callback").session(session))
            .andExpect(status().isOk())
            .andExpect(view().name("/all"));

        String accessToken = (String) session.getAttribute("accessToken");
        String refreshToken = (String) session.getAttribute("refreshToken");
        assertThat(accessToken)
            .isEqualTo(jwt.getAccessToken());
        assertThat(refreshToken)
            .isEqualTo(jwt.getRefreshToken());
    }

    private JwtTokenDto givingJwt() throws Exception {
        JwtTokenDto jwt = new JwtTokenDto();
        jwt.setAccessToken("jwt access token");
        jwt.setRefreshToken("jwt refresh token");

        given(commonService.getJWT(any(), any()))
            .willReturn(jwt);
        return jwt;
    }

    private void givingMember() {
        Member member = new Member();
        member.setEmail("abc@naver.com");
        member.setPassword("1234");

        List<String> authorities = new ArrayList<>();
        authorities.add("USER");
        member.setAuthorities(authorities);

        given(commonService.getMember(anyString()))
            .willReturn(member);
    }

    private void givingNaverUserData() {
        NaverUserData data = new NaverUserData();
        NaverUserDataResponse response = new NaverUserDataResponse();
        response.setEmail("abc@naver.com");
        data.setResponse(response);

        given(naverLoginService.getUserDataThroughAccessToken(anyString()))
            .willReturn(data);
    }

    private void givingNaverAccessToken() {
        NaverAccessToken oauthToken = new NaverAccessToken();
        oauthToken.setAccessToken("this is accesstoken");

        given(naverLoginService.getAccessToken(any(), any(), any()))
            .willReturn(oauthToken);
    }
}
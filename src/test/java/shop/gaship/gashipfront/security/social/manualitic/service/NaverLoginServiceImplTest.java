package shop.gaship.gashipfront.security.social.manualitic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import javax.servlet.http.HttpSession;
import org.assertj.core.util.Strings;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipfront.security.social.manualitic.adapter.NaverAdapter;
import shop.gaship.gashipfront.security.social.manualitic.dto.NaverAccessToken;
import shop.gaship.gashipfront.security.social.manualitic.dto.userdata.NaverUserData;
import shop.gaship.gashipfront.security.social.manualitic.dto.userdata.NaverUserDataResponse;
import shop.gaship.gashipfront.security.social.manualitic.service.impl.NaverLoginServiceImpl;
import shop.gaship.gashipfront.security.common.exception.CsrfProtectedException;
import shop.gaship.gashipfront.exceptions.RequestFailureThrow;

/**
 * packageName    : shop.gaship.gashipfront.security.social.service.dance
 * fileName       : NaverLoginServiceImplTest
 * author         : choi-gyeom-jun
 * date           : 2022-07-14
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-07-14        choi-gyeom-jun       ?????? ??????
 */

@ExtendWith(SpringExtension.class)
@Import(NaverLoginServiceImpl.class)
@TestPropertySource("classpath:application.properties")
//@ActiveProfiles(
//    value = {"dev"}
//)
class NaverLoginServiceImplTest {
    @Autowired
    private NaverLoginService naverLoginService;

    @MockBean
    private NaverAdapter adapter;

    private HttpSession session;

    @BeforeEach
    void setUp() {
        session = new MockHttpSession();
    }

    @AfterEach
    void after() {
        session.invalidate();
    }

    @Value("${naver-client-id}")
    private String clientId;

    @Value("${naver-redirect-url}")
    private String redirectUrl;

    @Value("${naver-api-url-login}")
    private String apiUrlForLogin;

    @DisplayName("naver??? oauth ????????? ???????????? ???????????? ????????? ????????? ???????????? ?????? ????????????.")
    @Test
    void getUriForLoginPageRequest() throws UnsupportedEncodingException, URISyntaxException {
        // when
        String fullUri = naverLoginService.getUriForLoginPageRequest();

        String[] parameter = fullUri.split("[?]")[1].split("&");
        String apiUriForLogin = fullUri.split("[?]")[0];

        String[] value = {
            "response_type=code",
            Strings.concat("client_id=", clientId),
            Strings.concat("redirect_uri=", URLEncoder.encode(redirectUrl, "UTF-8"))
        };

        // then
        assertThat(apiUriForLogin)
            .isEqualTo(Strings.concat("https://", this.apiUrlForLogin));

        for (int i = 0; i < value.length; i++) {
            assertThat(parameter[i])
                .isEqualTo(value[i]);
        }
    }


    @DisplayName("?????? ??????????????? url??? redirect??? ?????? ?????? state ?????? ????????? ?????????????????? redirect?????? state?????? ?????? ????????? ?????? ???????????? ????????? ????????????.")
    @Test
    void getAccessToken_state_success() throws UnsupportedEncodingException, URISyntaxException {
        // given
        String uri = naverLoginService.getUriForLoginPageRequest();
        String[] parameters = uri.split("&state=");

        String state = parameters[parameters.length - 1];
        String code = "this is code";

        NaverAccessToken givenToken = new NaverAccessToken();
        givenToken.setAccessToken("access");
        givenToken.setRefreshToken("refresh");

        given(adapter.requestNaverAccessToken(anyString()))
            .willReturn(givenToken);

        // when
        NaverAccessToken token = naverLoginService.getAccessToken(code, state, state);

        // then
        assertThat(token)
            .isEqualTo(givenToken);
    }

    @DisplayName("?????? ??????????????? url??? redirect??? ?????? ?????? state?????? ????????? ?????????????????? redirect?????? state?????? ????????? ????????? ?????? CsrfException??? ????????????.")
    @Test
    void getAccessToken_state_fail() throws UnsupportedEncodingException, URISyntaxException {
        // given
        naverLoginService.getUriForLoginPageRequest();

        String state = (String) session.getAttribute("state");
        String code = "this is code";

        NaverAccessToken givenToken = new NaverAccessToken();
        givenToken.setAccessToken("access");
        givenToken.setRefreshToken("refresh");

        given(adapter.requestNaverAccessToken(anyString()))
            .willReturn(givenToken);

        // when then
        assertThatThrownBy(() -> naverLoginService.getAccessToken(code, state, "failstate"))
            .isInstanceOf(CsrfProtectedException.class)
            .hasMessageContaining("csrf protect");
    }

    @DisplayName("naverUserData??? message?????? success????????? ?????? ??? ??????????????? ??? ?????? ????????? ??? ????????????.")
    @Test
    void getUserDataThroughAccessToken_success() {
        // given
        NaverUserData naverUserData = new NaverUserData();
        naverUserData.setMessage("success");

        NaverUserDataResponse naverUserDataResponse = new NaverUserDataResponse();
        naverUserDataResponse.setEmail("abc@naver.com");
        naverUserDataResponse.setName("?????????");
        naverUserData.setResponse(naverUserDataResponse);

        given(adapter.requestNaverUserData(anyString(), anyString()))
            .willReturn(naverUserData);
        // when
        NaverUserDataResponse response =
            naverLoginService.getUserDataThroughAccessToken("accessToken").getResponse();

        // then
        assertThat(response.getEmail())
            .isEqualTo("abc@naver.com");
        assertThat(response.getName())
            .isEqualTo("?????????");
    }

    @DisplayName("naverUserData??? message?????? success??? ???????????? ResponseDataException??? ???????????????.")
    @Test
    void getUserDataThroughAccessToken_fail() {
        // given
        NaverUserData naverUserData = new NaverUserData();
        naverUserData.setMessage("fail");

        NaverUserDataResponse naverUserDataResponse = new NaverUserDataResponse();
        naverUserDataResponse.setEmail("abc@naver.com");
        naverUserDataResponse.setName("?????????");
        naverUserData.setResponse(naverUserDataResponse);

        given(adapter.requestNaverUserData(anyString(), anyString()))
            .willReturn(naverUserData);

        // when then
        assertThatThrownBy(() -> naverLoginService.getUserDataThroughAccessToken("accessToken"))
            .isInstanceOf(RequestFailureThrow.class)
            .hasMessageContaining("message : ");
    }

}
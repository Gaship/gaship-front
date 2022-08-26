package shop.gaship.gashipfront.security.social.manualitic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.servlet.http.HttpSession;
import org.assertj.core.util.Strings;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.TestPropertySources;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipfront.config.OauthConfig;
import shop.gaship.gashipfront.config.RedisConfig;
import shop.gaship.gashipfront.config.SecureManagerConfig;
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
 * 2022-07-14        choi-gyeom-jun       최초 생성
 */

@ExtendWith(SpringExtension.class)
@Import({NaverLoginServiceImpl.class})
@EnableConfigurationProperties(value = {OauthConfig.class, SecureManagerConfig.class})
@TestPropertySources(value = {@TestPropertySource(value = "classpath:application.properties"), @TestPropertySource(value = "classpath:application-dev.properties")})
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

    @Autowired
    private OauthConfig oauthConfig;

    private String clientId;

    private String redirectUrl;

    private String apiUrlForLogin;

    @BeforeEach
    void before() {
        clientId = oauthConfig.getNaverClientId();
        redirectUrl = oauthConfig.getNaverRedirectUrl();
        apiUrlForLogin = oauthConfig.getNaverApiUrlLogin();
    }

    @DisplayName("naver로 oauth 했을때 설정에서 불러온뒤 조합한 값들이 설정값과 같게 잘나온다.")
    @Test
    void getUriForLoginPageRequest()
        throws IOException, URISyntaxException, UnrecoverableKeyException, CertificateException,
        NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
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


    @DisplayName("처음 로그인요청 url을 redirect로 줄때 만든 state 값과 사용자 로그인완료후 redirect될때 state값이 같게 들어온 경우 문제없이 메서드 진행된다.")
    @Test
    void getAccessToken_state_success()
        throws IOException, URISyntaxException, UnrecoverableKeyException, CertificateException,
        NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
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

    @DisplayName("처음 로그인요청 url을 redirect로 줄때 만든 state값과 사용자 로그인완료후 redirect될때 state값이 다르게 들어온 경우 CsrfException이 발생한다.")
    @Test
    void getAccessToken_state_fail()
        throws IOException, URISyntaxException, UnrecoverableKeyException, CertificateException,
        NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
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

    @DisplayName("naverUserData의 message값이 success인경우 값을 잘 바인딩하고 또 해당 객체를 잘 반환한다.")
    @Test
    void getUserDataThroughAccessToken_success() {
        // given
        NaverUserData naverUserData = new NaverUserData();
        naverUserData.setMessage("success");

        NaverUserDataResponse naverUserDataResponse = new NaverUserDataResponse();
        naverUserDataResponse.setEmail("abc@naver.com");
        naverUserDataResponse.setName("홍길동");
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
            .isEqualTo("홍길동");
    }

    @DisplayName("naverUserData의 message값이 success가 아닌경우 ResponseDataException을 발생시킨다.")
    @Test
    void getUserDataThroughAccessToken_fail() {
        // given
        NaverUserData naverUserData = new NaverUserData();
        naverUserData.setMessage("fail");

        NaverUserDataResponse naverUserDataResponse = new NaverUserDataResponse();
        naverUserDataResponse.setEmail("abc@naver.com");
        naverUserDataResponse.setName("홍길동");
        naverUserData.setResponse(naverUserDataResponse);

        given(adapter.requestNaverUserData(anyString(), anyString()))
            .willReturn(naverUserData);

        // when then
        assertThatThrownBy(() -> naverLoginService.getUserDataThroughAccessToken("accessToken"))
            .isInstanceOf(RequestFailureThrow.class)
            .hasMessageContaining("message : ");
    }

}
package shop.gaship.gashipfront.security.social.service.dance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
import org.assertj.core.util.Strings;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipfront.security.social.adapter.Adapter;
import shop.gaship.gashipfront.security.social.dto.accesstoken.NaverAccessToken;
import shop.gaship.gashipfront.security.social.dto.domain.Member;
import shop.gaship.gashipfront.security.social.dto.oauth.UserDetailsDto;
import shop.gaship.gashipfront.security.social.dto.userdata.NaverUserData;
import shop.gaship.gashipfront.security.social.dto.userdata.NaverUserDataResponse;
import shop.gaship.gashipfront.security.social.exception.CsrfProtectedException;
import shop.gaship.gashipfront.security.social.exception.ResponseDataException;

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

// TODO test2 : 서비스로직에서 설정값을 읽게하려면 SpringBootTest말곤 방법이 없는건가?
@ExtendWith(SpringExtension.class)
@Import(NaverLoginServiceImpl.class)
@TestPropertySource("classpath:application-dev.properties")
//@ActiveProfiles(
//    value = {"dev"}
//)
class NaverLoginServiceImplTest {
    @Autowired
    private NaverLoginService naverLoginService;

    @MockBean
    private Adapter adapter;

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

    @DisplayName("naver로 oauth 했을때 설정에서 불러온뒤 조합한 값들이 설정값과 같게 잘나온다.")
    @Test
    void getUriForLoginPageRequest() throws UnsupportedEncodingException {
        // when
        String fullUri = naverLoginService.getUriForLoginPageRequest(session);

        String[] parameter = fullUri.split("[?]")[1].split("&");
        String apiUriForLogin = fullUri.split("[&]")[0];

        String[] value = {
            "response_type=code",
            Strings.concat("client_id=", clientId),
            Strings.concat("redirect_uri=", URLEncoder.encode(redirectUrl))
        };

        // then
        assertThat(apiUriForLogin)
            .isEqualTo(this.apiUrlForLogin);

        for (int i = 0; i < value.length; i++) {
            assertThat(parameter[i])
                .isEqualTo(value[i]);
        }
    }

    @DisplayName("naver로 oauth 했을때 state를 잘 생성하고 생성한 값이 session에 잘 들어간다. ")
    @Test
    void getUriForLoginPageRequest_session() throws UnsupportedEncodingException {
        // when
        String fullUri = naverLoginService.getUriForLoginPageRequest(session);

        String[] parameter = fullUri.split("[?]")[1].split("&");
        String apiUriForLogin = fullUri.split("[&]")[0];

        assertThat(session.getAttribute("state"))
            .isEqualTo(parameter[3].split("=")[1]);
    }

    @DisplayName("처음 로그인요청 url을 redirect로 줄때 만든 state 값과 사용자 로그인완료후 redirect될때 state값이 같게 들어온 경우 문제없이 메서드 진행된다.")
    @Test
    void getAccessToken_state_success() throws UnsupportedEncodingException {
        // given
        String uri = naverLoginService.getUriForLoginPageRequest(session);
        String[] parameters = uri.split("&state=");

        String state = parameters[parameters.length - 1];
        String code = "this is code";

        NaverAccessToken givenToken = new NaverAccessToken();
        givenToken.setAccessToken("access");
        givenToken.setRefreshToken("refresh");

        given(adapter.requestNaverAccessToken(anyString()))
            .willReturn(givenToken);

        // when
        NaverAccessToken token = naverLoginService.getAccessToken(code, state, (String) session.getAttribute("state"));

        // then
        assertThat(token)
            .isEqualTo(givenToken);
    }

    @DisplayName("처음 로그인요청 url을 redirect로 줄때 만든 state값과 사용자 로그인완료후 redirect될때 state값이 다르게 들어온 경우 CsrfException이 발생한다.")
    @Test
    void getAccessToken_state_fail() throws UnsupportedEncodingException {
        // given
        naverLoginService.getUriForLoginPageRequest(session);

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
            .isInstanceOf(ResponseDataException.class)
            .hasMessageContaining("message : ");
    }

    @DisplayName("member값을 이용하여 UserDetailsDto를 생성하고 그 값이 SecurityContext에 잘들어간다.")
    @Test
    void setSecurityContext() {
        // given
        Member member = new Member();
        member.setEmail("abc@naver.com");
        member.setPassword("1234");

        List<String> authorities = new ArrayList<>();
        authorities.add("USER");
        member.setAuthorities(authorities);

        UserDetailsDto
            userDetailsDto = new UserDetailsDto(member.getEmail(), member.getPassword(), member.getAuthorities().stream()
            .map(i -> new SimpleGrantedAuthority("ROLE_" + i))
            .collect(Collectors.toList()), member);

        // when
        naverLoginService.setSecurityContext(member);

        // then
        SecurityContext context = SecurityContextHolder.getContext();
        UserDetailsDto resultDto = (UserDetailsDto) context.getAuthentication().getPrincipal();

        assertThat(resultDto)
            .isEqualTo(userDetailsDto);
    }
}
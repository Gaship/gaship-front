package shop.gaship.gashipfront.security.social.service.dance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import org.assertj.core.util.Strings;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipfront.security.social.adapter.Adapter;

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
@SpringBootTest
class NaverLoginServiceImplTest {
    @Autowired
    private NaverLoginServiceImpl naverLoginService;

    @Mock
    private Adapter adapter;

    @Value("${naver-client-id}")
    private String clientId;

    @Value("${naver-client-secret}")
    private String clientSecret;

    @Value("${naver-redirect-url}")
    private String redirectUrl;

    @Value("${naver-api-url-login}")
    private String apiUrlForLogin;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getUriForLoginPageRequest() throws UnsupportedEncodingException {
        // when
        String fullUri = naverLoginService.getUriForLoginPageRequest();

        String[] uri = fullUri.split("[?]");
        String apiUriForLogin = uri[0];

        String[] parameter = uri[1].split("&");

        String[] value = {
            "response_type=code",
            Strings.concat("client_id=", clientId),
            Strings.concat("redirect_uri", redirectUrl)
        };

        for (int i = 0; i < value.length; i++) {
            assertThat(parameter[i])
                .isEqualTo(value[i]);
        }
    }

    @Test
    void getAccessToken() {
    }

    @Test
    void getUserDataThroughAccessToken() {
    }
}
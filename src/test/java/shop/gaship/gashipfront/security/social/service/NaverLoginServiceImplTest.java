package shop.gaship.gashipfront.security.social.service;

import static org.junit.jupiter.api.Assertions.*;
import static reactor.core.publisher.Mono.when;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import shop.gaship.gashipfront.security.social.adapter.Adapter;
import shop.gaship.gashipfront.security.social.service.dance.NaverLoginService;

/**
 * packageName    : shop.gaship.gashipfront.security.social.service
 * fileName       : NaverLoginServiceImplTest
 * author         : choi-gyeom-jun
 * date           : 2022-07-12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-07-12        choi-gyeom-jun       최초 생성
 */

// @WebMvcTest(LoginService.class)
@SpringBootTest
@ActiveProfiles(
    value = {"dev"}
)
class NaverLoginServiceImplTest {
    @Autowired
    @Qualifier(value = "naverLoginServiceImpl")
    private NaverLoginService loginService;

    @MockBean
    private Adapter adapter;

    @BeforeEach
    void setUp() {

    }

    @Test
    void getUriForLoginPageRequest() throws UnsupportedEncodingException {
        // given


        // when
//        System.out.println(URLEncoder.encode("http://localhost:8080/callback"));
//        String uri = loginService.getUriForLoginPageRequest();
//
//
//        System.out.println(uri);
//        System.out.println("hi");
        // then
    }

    @Test
    void getAccessToken() {
    }

    @Test
    void getUserDataThroughAccessToken() {
    }

    @Test
    void getMember() {
    }

    @Test
    void requestJWT() {
    }
}
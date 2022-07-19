package shop.gaship.gashipfront.security.social.common.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static reactor.core.publisher.Mono.error;
import static reactor.core.publisher.Mono.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipfront.security.social.common.adapter.Adapter;
import shop.gaship.gashipfront.security.social.common.service.impl.CommonServiceImpl;
import shop.gaship.gashipfront.security.social.common.dto.Jwt;

/**
 * packageName    : shop.gaship.gashipfront.security.social.service.common
 * fileName       : ShoppingmallServiceImplTest
 * author         : choi-gyeom-jun
 * date           : 2022-07-14
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-07-14        choi-gyeom-jun       최초 생성
 */

@ExtendWith(SpringExtension.class)
@Import(CommonServiceImpl.class)
class CommonServiceImplTest {
    @Autowired
    private CommonService commonService;

    @MockBean
    private Adapter adapter;


    @DisplayName("식별번호와 권한을 파라미터로 jwt를 요청한 경우에 JwtTokenDto타입으로 잘 넘어온다.")
    @Test
    void getJWT_success() throws Exception {
        // given
        Integer identifyNo = 123242124;
        List<String> authorities = new ArrayList<>();
        authorities.add("USER");

        String accessToken = "this is access token";
        String refreshToken = "this is refresh token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Jwt dummyToken = new Jwt();
        dummyToken.setAccessToken(accessToken);
        dummyToken.setRefreshToken(refreshToken);

        given(adapter.requestJwt(any()))
            .willReturn(dummyToken);
        // when
        Jwt token = commonService.getJWT(identifyNo, authorities);

        // then
        verify(adapter).requestJwt(any());
        assertThat(token.getAccessToken())
            .isEqualTo(accessToken);
        assertThat(token.getRefreshToken())
            .isEqualTo(refreshToken);
    }
}
package shop.gaship.gashipfront.security.social.service.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static reactor.core.publisher.Mono.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipfront.security.social.adapter.Adapter;
import shop.gaship.gashipfront.security.social.dto.domain.Member;
import shop.gaship.gashipfront.security.social.dto.jwt.JwtTokenDto;
import shop.gaship.gashipfront.security.social.dto.jwt.SignInSuccessUserDetailsDto;

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

    // TODO test1 : 이런테스트는 안하는게 나은건가? (service에서 별다른 조치 없이 값만 반환하는 경우)
    @Test
    void getMember() {
        // given
        String email = "gbeovhsqhtka@naver.com";
        Member member = new Member();
        member.setEmail(email);

        // mocking
        given(adapter.requestMemberByEmail(anyString()))
            .willReturn(member);

        // when
        Member actualMember = commonService.getMember(email);

        // then
        assertThat(actualMember)
            .isEqualTo(member);
    }

    @DisplayName("식별번호와 권한을 파라미터로 jwt를 요청했고 반환받은 status가 201인경우에 JwtTokenDto타입으로 잘 넘어온다.")
    @Test
    void getJWT() throws Exception {
        // given
        Long identifyNo = 123242124L;
        List<String> authorities = new ArrayList<>();
        authorities.add("USER");

        String accessToken = "this is access token";
        String refreshToken = "this is refresh token";

        JwtTokenDto dummyToken = new JwtTokenDto();
        dummyToken.setAccessToken(accessToken);
        dummyToken.setRefreshToken(refreshToken);

        given(adapter.requestJwt(any()))
            .willReturn(dummyToken);

        // when
        JwtTokenDto token = commonService.getJWT(identifyNo, authorities);

        // then
        assertThat(token.getAccessToken())
            .isEqualTo(accessToken);
        assertThat(token.getRefreshToken())
            .isEqualTo(refreshToken);
    }
}
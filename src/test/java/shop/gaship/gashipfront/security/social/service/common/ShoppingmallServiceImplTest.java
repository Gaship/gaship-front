package shop.gaship.gashipfront.security.social.service.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static reactor.core.publisher.Mono.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipfront.security.social.adapter.Adapter;
import shop.gaship.gashipfront.security.social.dto.domain.Member;

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
@Import(ShoppingmallServiceImpl.class)
class ShoppingmallServiceImplTest {
    @Autowired
    private ShoppingmallService shoppingmallService;

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
        Member actualMember = shoppingmallService.getMember(email);

        // then
        assertThat(actualMember)
            .isEqualTo(member);
    }

    @Test
    void getJWT() {
    }
}
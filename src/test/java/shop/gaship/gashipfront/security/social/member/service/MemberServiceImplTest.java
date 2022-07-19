package shop.gaship.gashipfront.security.social.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipfront.security.social.common.adapter.Adapter;
import shop.gaship.gashipfront.security.social.member.dto.Member;
import shop.gaship.gashipfront.security.social.member.service.impl.MemberServiceImpl;

/**
 * @author : 최겸준
 * @since 1.0
 */

@ExtendWith(SpringExtension.class)
@Import(MemberServiceImpl.class)
class MemberServiceImplTest {
    @Autowired
    private MemberService memberService;

    @MockBean
    private Adapter adapter;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @DisplayName("api서버에서 올바른 응답이 온 경우에 더미 member가 잘 반환된다.")
    @Test
    void getMemberByEmail() {
        // given
        String email = "gbeovhsqhtka@naver.com";
        Member member = new Member();
        member.setEmail(email);

        ResponseEntity<Object> response
            = ResponseEntity.status(200)
            .contentType(MediaType.APPLICATION_JSON)
            .body(member);

        // mocking
        given(adapter.requestMemberByEmail(anyString()))
            .willReturn(member);

        // when
        Member actualMember = memberService.getMemberByEmail(email);

        // then
        assertThat(actualMember)
            .isEqualTo(member);
    }

    @DisplayName("member가 회원가입하기위한 암호화기능, setter기능, 회원가입요청기능 메서드가 잘 동작한다.")
    @Test
    void createMember() {
        // given
        Member member = mock(Member.class);
        given(member.getPassword()).willReturn("zzz");
        given(passwordEncoder.encode(anyString()))
            .willReturn("abcd");

        // TODO : 왜쓰는거지
        doNothing().when(adapter).requestCreateMember(any());

        // when
        memberService.createMember(member);

        // then
        verify(passwordEncoder).encode("zzz");
        verify(member).setPassword("abcd");
        verify(adapter).requestCreateMember(member);
    }

    @DisplayName("adapter를 통해 반환된 현재 가입된 마지막 회원번호를 반환한다.")
    @Test
    void getLastMemberNo() {
        // given
        given(adapter.requestLastMemberNo())
            .willReturn(1);

        // when
        Integer memberNo = memberService.getLastMemberNo();

        // then
        assertThat(memberNo)
            .isEqualTo(1);
    }
}
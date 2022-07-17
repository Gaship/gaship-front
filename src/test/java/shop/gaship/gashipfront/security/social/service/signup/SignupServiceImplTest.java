package shop.gaship.gashipfront.security.social.service.signup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipfront.security.social.adapter.Adapter;
import shop.gaship.gashipfront.security.social.dto.domain.MemberCreationRequest;

/**
 * @author : 최겸준
 * @since 1.0
 */

@ExtendWith(SpringExtension.class)
@Import({SignupServiceImpl.class, BCryptPasswordEncoder.class})
class SignupServiceImplTest {
    @Autowired
    private SignupService signupService;

    @MockBean
    private Adapter adapter;

    @Test
    void createMember() {
        // given
        MemberCreationRequest memberCreationRequest
            = MemberCreationRequest.builder()
                .recommendMemberNo(12)
                .email("gbeovhsqhtka@naver.com")
                .password("nhnacademy1234!@#")
                .phoneNumber("01011111111")
                .name("홍길동")
                .nickName("닉넴홍1")
                .gender("남")
                .isUniqueEmail(Boolean.TRUE)
                .isVerifiedEmail(Boolean.TRUE)
                    .build();

        given(adapter.requestCreateMember(any()))
            .willReturn(Boolean.TRUE);

        // when
        Boolean isSuccess = signupService.createMember(memberCreationRequest);

        // then
        assertThat(isSuccess)
            .isTrue();
    }
}
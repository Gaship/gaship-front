package shop.gaship.gashipfront.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.member.adaptor.MemberAdaptor;
import shop.gaship.gashipfront.member.dto.MemberCreationRequest;
import shop.gaship.gashipfront.member.dto.MemberNumberPresence;

/**
 * 설명작성란
 *
 * @author 김민수
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberAdaptor memberAdaptor;
    private final PasswordEncoder passwordEncoder;

    public boolean executeSignUp(MemberCreationRequest memberCreationRequest) {
        String hashedPassword = passwordEncoder.encode(memberCreationRequest.getPassword());
        memberCreationRequest.changeHashedPassword(hashedPassword);

        return memberAdaptor.signUpRequest(memberCreationRequest);
    }

    public boolean checkDuplicatedEmail(String email) {
        return memberAdaptor.emailDuplicationCheckRequest(email)
            .getHasEmail();
    }

    public boolean checkDuplicatedNickname(String nickname) {
        return memberAdaptor.nicknameDuplicationCheckRequest(nickname)
            .getHasNickname();
    }

    public MemberNumberPresence findRecommendMemberNo(String nickname) {
        return memberAdaptor.recommendMemberNoFind(nickname);
    }
}

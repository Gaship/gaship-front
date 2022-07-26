package shop.gaship.gashipfront.member.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.member.adapter.MemberAdapter;
import shop.gaship.gashipfront.member.dto.MemberCreationRequest;
import shop.gaship.gashipfront.member.dto.MemberNumberPresence;
import shop.gaship.gashipfront.member.service.MemberService;
import shop.gaship.gashipfront.exceptions.RequestFailureThrow;
import shop.gaship.gashipfront.member.dto.MemberAllFieldDto;

/**
 * MemberService의 구현체입니다.
 *
 * @author : 최겸준
 * @see MemberService
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberAdapter memberAdapter;
    private final PasswordEncoder passwordEncoder;

    @Override
    public MemberAllFieldDto getMemberByEmail(String email) throws RequestFailureThrow {
        return memberAdapter.requestMemberByEmail(email);
    }

    @Override
    public void createMember(MemberAllFieldDto member) {
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        memberAdapter.requestCreateMember(member);
    }

    @Override
    public Integer getLastMemberNo() {
        return memberAdapter.requestLastMemberNo();
    }

    public boolean executeSignUp(MemberCreationRequest memberCreationRequest) {
        String hashedPassword = passwordEncoder.encode(memberCreationRequest.getPassword());
        memberCreationRequest.changeHashedPassword(hashedPassword);


        return memberAdapter.signUpRequest(memberCreationRequest);
    }

    public boolean checkDuplicatedEmail(String email) {
        return memberAdapter.emailDuplicationCheckRequest(email)
            .getHasEmail();
    }

    public boolean checkDuplicatedNickname(String nickname) {
        return memberAdapter.nicknameDuplicationCheckRequest(nickname)
            .getMemberNo() != null;
    }

    public MemberNumberPresence findRecommendMemberNo(String nickname) {
        return memberAdapter.recommendMemberNoFind(nickname);
    }
}

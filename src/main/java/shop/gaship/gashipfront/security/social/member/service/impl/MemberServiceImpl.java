package shop.gaship.gashipfront.security.social.member.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.security.social.common.adapter.Adapter;
import shop.gaship.gashipfront.security.social.common.exception.RequestFailureException;
import shop.gaship.gashipfront.security.social.member.dto.Member;
import shop.gaship.gashipfront.security.social.member.service.MemberService;

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
    private final Adapter adapter;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member getMemberByEmail(String email) throws RequestFailureException {
        return adapter.requestMemberByEmail(email);
    }
    @Override
    public void createMember(Member member) {
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        adapter.requestCreateMember(member);
    }

    @Override
    public Integer getLastMemberNo() {
        return adapter.requestLastMemberNo();
    }
}
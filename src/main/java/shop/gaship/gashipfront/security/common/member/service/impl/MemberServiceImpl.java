package shop.gaship.gashipfront.security.common.member.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.security.common.member.adapter.MemberAdapter;
import shop.gaship.gashipfront.security.common.member.service.MemberService;
import shop.gaship.gashipfront.security.common.exception.RequestFailureException;
import shop.gaship.gashipfront.security.common.member.dto.MemberDto;

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
    private final MemberAdapter adapter;
    private final PasswordEncoder passwordEncoder;

    @Override
    public MemberDto getMemberByEmail(String email) throws RequestFailureException {
        return adapter.requestMemberByEmail(email);
    }

    @Override
    public void createMember(MemberDto member) {
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        adapter.requestCreateMember(member);
    }

    @Override
    public Integer getLastMemberNo() {
        return adapter.requestLastMemberNo();
    }
}

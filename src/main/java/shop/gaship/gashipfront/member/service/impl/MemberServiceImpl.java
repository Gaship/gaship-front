package shop.gaship.gashipfront.member.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.exceptions.RequestFailureThrow;
import shop.gaship.gashipfront.member.adapter.MemberAdapter;
import shop.gaship.gashipfront.member.dto.MemberAllFieldDto;
import shop.gaship.gashipfront.member.dto.MemberNumberPresence;
import shop.gaship.gashipfront.member.dto.request.MemberCreationRequest;
import shop.gaship.gashipfront.member.dto.request.MemberModifyByAdminDto;
import shop.gaship.gashipfront.member.dto.request.MemberModifyRequestDto;
import shop.gaship.gashipfront.member.dto.response.MemberResponseByAdminDto;
import shop.gaship.gashipfront.member.dto.response.MemberResponseDto;
import shop.gaship.gashipfront.member.service.MemberService;
import shop.gaship.gashipfront.util.dto.PageResponse;

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

    @Override
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void modifyMember(MemberModifyRequestDto request) {
        memberAdapter.modifyMember(request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void modifyMemberByAdmin(MemberModifyByAdminDto request) {
        memberAdapter.modifyMemberByAdmin(request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeMember(Integer memberNo) {
        memberAdapter.removeMember(memberNo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MemberResponseDto findMember(Integer memberNo) {
        return memberAdapter.findMember(memberNo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MemberResponseByAdminDto findMemberByAdmin(Integer memberNo) {
        return memberAdapter.findMemberByAdmin(memberNo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponse<MemberResponseByAdminDto> findMembers(Pageable pageable) {
        return memberAdapter.findMembers(pageable);
    }

}

package shop.gaship.gashipfront.security.social.member.service;

import shop.gaship.gashipfront.security.social.member.dto.Member;

/**
 * 회원가입을 위한 service레이어의 클래스입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
public interface MemberService {

    Member getMemberByEmail(String email);
    /**
     * @param member creation request
     * @return boolean
     * @author 최겸준
     */
    void createMember(Member memberCreationRequest);

    Integer getLastMemberNo();
}

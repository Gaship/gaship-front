package shop.gaship.gashipfront.security.common.adapter.shoppingmallapi;

import shop.gaship.gashipfront.security.social.member.dto.Member;

/**
 * api서버에 요청을 처리하는 기능을 담당하는 interface입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
public interface ShoppingMallAPIAdapter {
    /**
     * gaship-shopping-mall api에 email을 통해서 member를 요청하는 기능입니다.
     *
     * @param email email을 저장하는 변수입니다.
     * @return responseEntity
     * @author 최겸준
     */
    Member requestMemberByEmail(String email);

    /**
     * 멤버의 회원가입 요청을 담당하는 기능입니다.
     *
     * @param member 회원가입시 필요한 정보를 담고있는 Memeber객체입니다.
     * @author 최겸준
     */
    void requestCreateMember(Member member);

    /**
     * 멤버의 회원가입시 닉네임생성을 위해 최신 번호를 가져오는 기능입니다.
     *
     * @author 최겸준
     */
    Integer requestLastMemberNo();
}

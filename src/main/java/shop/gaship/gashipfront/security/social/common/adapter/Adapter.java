package shop.gaship.gashipfront.security.social.common.adapter;

import shop.gaship.gashipfront.security.social.dance.dto.NaverAccessToken;
import shop.gaship.gashipfront.security.social.dance.dto.userdata.NaverUserData;
import shop.gaship.gashipfront.security.social.member.dto.Member;
import shop.gaship.gashipfront.security.social.common.dto.JwtDto;
import shop.gaship.gashipfront.security.social.common.dto.SigninSuccessUserDetailsDto;

/**
 * api서버에 요청을 처리하는 기능을 담당하는 interface입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
public interface Adapter {
    /**
     * 네이버에 accessToken을 요청하고 token을 받아오는 기능입니다.
     *
     * @param uri naver의 access token을 받기위해서 webclient로 전송할 url이며 내부에는 code, client_id, client_secret이 함께 있습니다.
     * @return naverAccessToken code값을 통해서 받아온 access token을  NaverAccessToken 타입으로 반환한 값을 담는 변수입니다.
     * @author 최겸준
     */
    NaverAccessToken requestNaverAccessToken(String uri);

    /**
     * access token을 가지고 네이버에 회원의 데이터를 요구하고 받아오는 기능입니다.
     *
     * @param apiUrlForUserData 회원의 데이터를 요구할때 요청할 url입니다.
     * @param accessToken 데이터를 요구하기전에 인가되었음을 증명해야하는 토큰입니다.
     * @return naverUserData 건네받은 유저정보를 보관하는 변수입니다.
     * @author 최겸준
     */
    NaverUserData requestNaverUserData(String apiUrlForUserData, String accessToken);

    /**
     * gaship-shopping-mall api에 email을 통해서 member를 요청하는 기능입니다.
     *
     * @param email email을 저장하는 변수입니다.
     * @return responseEntity
     * @author 최겸준
     */
    Member requestMemberByEmail(String email);

    /**
     * shopping-mall API에 jwt 토큰을 요청하고 반환받는 기능입니다.
     *
     * @param detailsDto 요청을 보낼때 담아줘야하는 정보객체입니다.
     * @return Jwt jwt 토큰을 반환합니다.
     * @author 최겸준
     */
    JwtDto requestJwt(SigninSuccessUserDetailsDto detailsDto);

    /**
     * 멤버의 회원가입 요청을 담당하는 기능입니다.
     *
     * @param member 회원가입시 필요한 정보를 담고있는 Memeber객체입니다.
     * @author 최겸준
     */
    void requestCreateMember(Member member);

    Integer requestLastMemberNo();
}

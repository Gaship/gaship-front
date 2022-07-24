package shop.gaship.gashipfront.security.common.adapter.oauth;

import shop.gaship.gashipfront.security.social.manualitic.dto.NaverAccessToken;
import shop.gaship.gashipfront.security.social.manualitic.dto.userdata.NaverUserData;

/**
 * naver login을 이용하기위한 naver api adapter입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
public interface NaverAdapter {
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
}

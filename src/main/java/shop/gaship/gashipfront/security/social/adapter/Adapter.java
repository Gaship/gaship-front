package shop.gaship.gashipfront.security.social.adapter;


import shop.gaship.gashipfront.security.social.dto.Member;
import shop.gaship.gashipfront.security.social.dto.NaverAccessToken;
import shop.gaship.gashipfront.security.social.dto.NaverUserData;
import shop.gaship.gashipfront.security.social.dto.PaycoAccessToken;

public interface Adapter {
    // TODO 4 : refactoring필요 확장성고려 하나의 메서드로 만들기
    NaverAccessToken requestNaverAccessToken(String uri);
    PaycoAccessToken requestPaycoAccessToken(String uri);

    NaverUserData requestNaverUserData(String apiUrlForUserData, String accessToken);
    void requestPaycoInfo();

    Member requestMemberByMobile(String mobile);
}

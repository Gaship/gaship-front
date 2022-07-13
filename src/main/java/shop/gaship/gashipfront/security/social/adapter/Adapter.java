package shop.gaship.gashipfront.security.social.adapter;

import shop.gaship.gashipfront.security.social.dto.accesstoken.NaverAccessToken;
import shop.gaship.gashipfront.security.social.dto.domain.Member;
import shop.gaship.gashipfront.security.social.dto.userdata.NaverUserData;

public interface Adapter {
    NaverAccessToken requestNaverAccessToken(String uri);
    NaverUserData requestNaverUserData(String apiUrlForUserData, String accessToken);
    Member requestMemberByMobile(String mobile);
}

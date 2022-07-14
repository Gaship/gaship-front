package shop.gaship.gashipfront.security.social.service.dance;

import java.io.UnsupportedEncodingException;
import shop.gaship.gashipfront.security.social.dto.accesstoken.NaverAccessToken;
import shop.gaship.gashipfront.security.social.dto.domain.Member;
import shop.gaship.gashipfront.security.social.dto.userdata.NaverUserData;

public interface NaverLoginService {
    String getUriForLoginPageRequest() throws UnsupportedEncodingException;
    NaverAccessToken getAccessToken(String code, String state);

    NaverUserData getUserDataThroughAccessToken(String accessToken);
}

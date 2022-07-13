package shop.gaship.gashipfront.security.social.service;

import java.io.UnsupportedEncodingException;
import shop.gaship.gashipfront.security.social.dto.Member;
import shop.gaship.gashipfront.security.social.dto.NaverAccessToken;
import shop.gaship.gashipfront.security.social.dto.NaverUserData;

public interface LoginService {
    String getUriForLoginPageRequest() throws UnsupportedEncodingException;
    NaverAccessToken getAccessToken(String code, String state);

    NaverUserData getUserDataThroughAccessToken(String accessToken);

    Member getMember(String mobile);

    String requestJWT(String id, String email) throws Exception;
}

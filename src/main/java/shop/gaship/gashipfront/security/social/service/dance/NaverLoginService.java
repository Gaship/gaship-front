package shop.gaship.gashipfront.security.social.service.dance;

import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpSession;
import shop.gaship.gashipfront.security.social.dto.accesstoken.NaverAccessToken;
import shop.gaship.gashipfront.security.social.dto.domain.Member;
import shop.gaship.gashipfront.security.social.dto.userdata.NaverUserData;

public interface NaverLoginService {
    String getUriForLoginPageRequest(HttpSession session) throws UnsupportedEncodingException;

    NaverAccessToken getAccessToken(String code, String parameterState, String redisState);

    NaverUserData getUserDataThroughAccessToken(String accessToken);

    void setSecurityContext(Member member);
}

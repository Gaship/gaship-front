package shop.gaship.gashipfront.security.social.dance.service;

import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpSession;
import shop.gaship.gashipfront.security.social.dance.dto.NaverAccessToken;
import shop.gaship.gashipfront.security.social.dance.dto.userdata.NaverUserData;
import shop.gaship.gashipfront.security.social.member.dto.Member;


/**
 * Oauth Dance를 통해 naver에 로그인할시에 controller에서 사용될 service입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
public interface NaverLoginService {
    /**
     * Gets uri for login page request.
     *
     * @param session the session
     * @return the uri for login page request
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    String getUriForLoginPageRequest(HttpSession session) throws UnsupportedEncodingException;

    /**
     * Gets access token.
     *
     * @param code           the code
     * @param parameterState the parameter state
     * @param redisState     the redis state
     * @return the access token
     */
    NaverAccessToken getAccessToken(String code, String parameterState, String redisState);

    /**
     * Gets user data through access token.
     *
     * @param accessToken the access token
     * @return the user data through access token
     */
    NaverUserData getUserDataThroughAccessToken(String accessToken);

    /**
     * Sets security context.
     *
     * @param member the member
     */
    void setSecurityContext(Member member);
}

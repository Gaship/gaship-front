package shop.gaship.gashipfront.security.social.manualitic.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URI;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.Objects;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.security.common.adapter.oauth.NaverAdapter;
import shop.gaship.gashipfront.security.social.manualitic.dto.NaverAccessToken;
import shop.gaship.gashipfront.security.social.manualitic.dto.userdata.NaverUserData;
import shop.gaship.gashipfront.security.social.manualitic.service.NaverLoginService;
import shop.gaship.gashipfront.security.common.exception.CsrfProtectedException;
import shop.gaship.gashipfront.security.common.exception.RequestFailureException;


/**
 * NaverLoginService 의 구현체입니다.
 *
 * @author : 최겸준
 * @see NaverLoginService
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class NaverLoginServiceImpl implements NaverLoginService {
    @Value("${naver-client-id}")
    private String clientId;

    @Value("${naver-client-secret}")
    private String clientSecret;

    @Value("${naver-redirect-url}")
    private String redirectUrl;

    @Value("${naver-api-url-login}")
    private String apiUrlForLogin;

    @Value("${naver-api-url-accesstoken}")
    private String apiUrlForAccessToken;

    @Value("${naver-api-url-userdata}")
    private String apiUrlForUserData;

    private final NaverAdapter adapter;

    @Override
    public String getUriForLoginPageRequest() throws UnsupportedEncodingException {
        BigInteger state = new BigInteger(130, new SecureRandom());

        StringBuilder uriForLoginPageRequest = new StringBuilder();

//        URI uri = new URIBuilder()
//            .setScheme("http")
//            .setHost(apiUrlForLogin)
//            .setParameter("").build();
        uriForLoginPageRequest
            .append(apiUrlForLogin)
            .append("&client_id=").append(clientId)
            // TODO : 이거안해도 webclient에서 encode 들어가나? adapter에서 주는게 맞나?
            .append("&redirect_uri=").append(URLEncoder.encode(redirectUrl, "UTF-8"))
            .append("&state=").append(state.toString());

        return uriForLoginPageRequest.toString();
    }

    @Override
    public NaverAccessToken getAccessToken(String code, String parameterState, String redisState) {
        if (!Objects.equals(parameterState, redisState)) throw new CsrfProtectedException("csrf protect");

        StringBuilder uriForAccessToken = new StringBuilder();
        uriForAccessToken
            .append(apiUrlForAccessToken)
            .append("&client_id=").append(clientId)
            .append("&client_secret=").append(clientSecret)
            .append("&code=").append(code);

        return adapter.requestNaverAccessToken(uriForAccessToken.toString());
    }

    @Override
    public NaverUserData getUserDataThroughAccessToken(String accessToken) {
        NaverUserData data = adapter.requestNaverUserData(apiUrlForUserData, accessToken);
        if (!Objects.equals(data.getMessage(), "success")) throw new RequestFailureException("oauth request fail, message : " + data.getMessage());
        return data;
    }
}

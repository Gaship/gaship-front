package shop.gaship.gashipfront.security.social.manualitic.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.SecureRandom;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.security.social.manualitic.adapter.NaverAdapter;
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
    public String getUriForLoginPageRequest()
        throws UnsupportedEncodingException, URISyntaxException {
        BigInteger state = new BigInteger(130, new SecureRandom());

        URI uri = new URIBuilder()
            .setScheme("https")
            .setHost(apiUrlForLogin)
            .setParameter("response_type", "code")
            .setParameter("client_id", clientId)
            .setParameter("redirect_uri", redirectUrl)
            .setParameter("state", state.toString())
            .build();

        return uri.toString();
    }

    @Override
    public NaverAccessToken getAccessToken(String code, String parameterState, String redisState) {
        if (!Objects.equals(parameterState, redisState)) throw new CsrfProtectedException("csrf protect");

        URIBuilder uriBuilder = new URIBuilder()
            .setScheme("https")
            .setHost(apiUrlForAccessToken)
            .setParameter("grant_type", "authorization_code")
            .setParameter("client_id", clientId)
            .setParameter("client_secret", clientSecret)
            .setParameter("code", code);

        return adapter.requestNaverAccessToken(uriBuilder.toString());
    }

    @Override
    public NaverUserData getUserDataThroughAccessToken(String accessToken) {
        NaverUserData data = adapter.requestNaverUserData(apiUrlForUserData, accessToken);
        if (!Objects.equals(data.getMessage(), "success")) throw new RequestFailureException("oauth request fail, message : " + data.getMessage());
        return data;
    }
}

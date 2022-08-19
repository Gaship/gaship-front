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
import shop.gaship.gashipfront.config.OauthConfig;
import shop.gaship.gashipfront.exceptions.RequestFailureThrow;
import shop.gaship.gashipfront.security.common.exception.CsrfProtectedException;
import shop.gaship.gashipfront.security.social.manualitic.adapter.NaverAdapter;
import shop.gaship.gashipfront.security.social.manualitic.dto.NaverAccessToken;
import shop.gaship.gashipfront.security.social.manualitic.dto.userdata.NaverUserData;
import shop.gaship.gashipfront.security.social.manualitic.service.NaverLoginService;


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

    private final OauthConfig oauthConfig;
    private final NaverAdapter adapter;

    @Override
    public String getUriForLoginPageRequest()
        throws UnsupportedEncodingException, URISyntaxException {
        BigInteger state = new BigInteger(130, new SecureRandom());

        URI uri = new URIBuilder()
            .setScheme("https")
            .setHost(oauthConfig.getNaverApiUrlLogin())
            .setParameter("response_type", "code")
            .setParameter("client_id", oauthConfig.getNaverClientId())
            .setParameter("redirect_uri", oauthConfig.getNaverRedirectUrl())
            .setParameter("state", state.toString())
            .build();

        return uri.toString();
    }

    @Override
    public NaverAccessToken getAccessToken(String code, String parameterState, String redisState) {
        if (!Objects.equals(parameterState, redisState)) {
            throw new CsrfProtectedException("csrf protect");
        }

        URIBuilder uriBuilder = new URIBuilder()
            .setScheme("https")
            .setHost(oauthConfig.getNaverApiUrlAcesstoken())
            .setParameter("grant_type", "authorization_code")
            .setParameter("client_id", oauthConfig.getNaverClientId())
            .setParameter("client_secret", oauthConfig.getNaverClientSecret())
            .setParameter("code", code);

        return adapter.requestNaverAccessToken(uriBuilder.toString());
    }

    @Override
    public NaverUserData getUserDataThroughAccessToken(String accessToken) {
        NaverUserData data = adapter.requestNaverUserData(oauthConfig.getNaverApiUrlUserData(), accessToken);
        if (!Objects.equals(data.getMessage(), "success")) {
            throw new RequestFailureThrow("oauth request fail, message : " + data.getMessage());
        }
        return data;
    }
}

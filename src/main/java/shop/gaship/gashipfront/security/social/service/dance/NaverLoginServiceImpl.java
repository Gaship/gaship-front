package shop.gaship.gashipfront.security.social.service.dance;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.security.social.adapter.Adapter;
import shop.gaship.gashipfront.security.social.dto.accesstoken.NaverAccessToken;
import shop.gaship.gashipfront.security.social.dto.domain.Member;
import shop.gaship.gashipfront.security.social.dto.userdata.NaverUserData;
import shop.gaship.gashipfront.security.social.exception.ReceiveDataException;

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

    // TODO need4 : redis에 sessionId를 이용해서 집어넣어야함. 안그러면 로드밸런싱시에 문제일어남 레디스에 넣고, 필드의 생성도 지역범위로 변경
    private BigInteger state;

    private final Adapter adapter;

    @Override
    public String getUriForLoginPageRequest() throws UnsupportedEncodingException {
        state = new BigInteger(130, new SecureRandom());

        StringBuilder uriForLoginPageRequest = new StringBuilder();
        uriForLoginPageRequest
            .append(apiUrlForLogin)
            .append("&client_id=").append(clientId)
            .append("&redirect_uri=").append(URLEncoder.encode(redirectUrl, "UTF-8"))
            .append("&state=").append(state.toString());

        return uriForLoginPageRequest.toString();
    }

    @Override
    public NaverAccessToken getAccessToken(String code, String state) {
        if (!Objects.equals(state, this.state.toString())) throw new RuntimeException("csrf protect");

        StringBuilder uriForAccessToken = new StringBuilder();
        uriForAccessToken
            .append(apiUrlForAccessToken)
            .append("&client_id=").append(clientId)
            .append("&client_secret=").append(clientSecret)
            .append("&code=").append(code);
//            .append("&state=").append(this.state);

        return adapter.requestNaverAccessToken(uriForAccessToken.toString());
    }

    @Override
    public NaverUserData getUserDataThroughAccessToken(String accessToken) {
        NaverUserData data = adapter.requestNaverUserData(apiUrlForUserData, accessToken);
        if (!Objects.equals(data.getMessage(), "success")) throw new ReceiveDataException("message : " + data.getMessage());
        return data;
    }

    @Override
    public Member getMember(String mobile) {
        return adapter.requestMemberByMobile(mobile);
    }

    @Override
    public String requestJWT(String id, String email) throws Exception {
        return null;
    }
}

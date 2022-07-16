package shop.gaship.gashipfront.security.social.service.dance;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import shop.gaship.gashipfront.security.social.adapter.Adapter;
import shop.gaship.gashipfront.security.social.dto.accesstoken.NaverAccessToken;
import shop.gaship.gashipfront.security.social.dto.domain.Member;
import shop.gaship.gashipfront.security.social.dto.oauth.UserDetailsDto;
import shop.gaship.gashipfront.security.social.dto.userdata.NaverUserData;
import shop.gaship.gashipfront.security.social.exception.CsrfProtectedException;
import shop.gaship.gashipfront.security.social.exception.ResponseDataException;

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
    private final Adapter adapter;

    @Override
    public String getUriForLoginPageRequest(HttpSession session) throws UnsupportedEncodingException {
        BigInteger state = new BigInteger(130, new SecureRandom());
        session.setAttribute("state", String.valueOf(state));

        StringBuilder uriForLoginPageRequest = new StringBuilder();
        uriForLoginPageRequest
            .append(apiUrlForLogin)
            .append("&client_id=").append(clientId)
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
        if (!Objects.equals(data.getMessage(), "success")) throw new ResponseDataException("message : " + data.getMessage());
        return data;
    }

    @Override
    public void setSecurityContext(Member member) {
        UserDetailsDto userDetailsDto = new UserDetailsDto(member.getEmail(), member.getPassword(), member.getAuthorities().stream()
            .map(i -> new SimpleGrantedAuthority("ROLE_" + i))
            .collect(Collectors.toList()), member);

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetailsDto, null,
            userDetailsDto.getAuthorities());
        context.setAuthentication(authentication);
    }
}

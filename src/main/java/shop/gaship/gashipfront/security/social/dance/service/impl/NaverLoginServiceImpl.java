package shop.gaship.gashipfront.security.social.dance.service.impl;

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
import shop.gaship.gashipfront.security.common.adapter.Adapter;
import shop.gaship.gashipfront.security.social.dance.dto.NaverAccessToken;
import shop.gaship.gashipfront.security.social.dance.dto.userdata.NaverUserData;
import shop.gaship.gashipfront.security.social.dance.service.NaverLoginService;
import shop.gaship.gashipfront.security.social.member.dto.Member;
import shop.gaship.gashipfront.security.common.dto.UserDetailsDto;
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
    private final Adapter adapter;

    @Override
    public String getUriForLoginPageRequest(HttpSession session) throws UnsupportedEncodingException {
        BigInteger state = new BigInteger(130, new SecureRandom());
        session.setAttribute("state", String.valueOf(state));

        StringBuilder uriForLoginPageRequest = new StringBuilder();
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

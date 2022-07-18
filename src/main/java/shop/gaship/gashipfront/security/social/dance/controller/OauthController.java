package shop.gaship.gashipfront.security.social.dance.controller;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import shop.gaship.gashipfront.security.social.common.service.CommonService;
import shop.gaship.gashipfront.security.social.dance.dto.userdata.NaverUserData;
import shop.gaship.gashipfront.security.social.member.dto.Member;
import shop.gaship.gashipfront.security.social.dance.dto.NaverAccessToken;
import shop.gaship.gashipfront.security.social.common.dto.Jwt;
import shop.gaship.gashipfront.security.social.dance.service.NaverLoginService;
import shop.gaship.gashipfront.security.social.common.util.SignupManager;
import shop.gaship.gashipfront.security.social.member.service.MemberService;

/**
 * Oauth2를 이용하지않고 Oauth Dance를 직접 구현하기위해 만든 컨트롤러입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
@Controller
@RequestMapping("/securities")
@RequiredArgsConstructor
@Slf4j
public class OauthController {
    private final NaverLoginService naverLoginService;
    private final CommonService commonService;
    private final MemberService memberService;

    /**
     * @param session
     * @return response entity
     * @throws UnsupportedEncodingException the unsupported encoding exception
     * @author 최겸준
     */
    @GetMapping("/login/naver")
    @ResponseBody
    public ResponseEntity<String> redirectUriForLoginPageRequestNaver(HttpSession session) throws UnsupportedEncodingException {
        HttpHeaders headers = new HttpHeaders();
        String uriForLoginPageRequest = naverLoginService.getUriForLoginPageRequest(session);
        headers.setLocation(URI.create(uriForLoginPageRequest));
        log.debug("uriForLoginPageRequest : {}", uriForLoginPageRequest);

        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    /**
     * Gets access token and authenticate naver.
     *
     * @param code           the code
     * @param parameterState the parameter state
     * @param session        the session
     * @return the access token and authenticate naver
     * @throws Exception the exception
     */
    @GetMapping("/login/naver/callback")
    public String getAccessTokenAndAuthenticateNaver(String code, String parameterState, HttpSession session) throws Exception {
        String redisState = (String) session.getAttribute("state");
        NaverAccessToken naverAccessToken = naverLoginService.getAccessToken(code, parameterState, redisState);

        NaverUserData data = naverLoginService.getUserDataThroughAccessToken(naverAccessToken.getAccessToken());

        SignupManager signupManager = new SignupManager(memberService);
        Member member = signupManager.getMember(data.getResponse());

        naverLoginService.setSecurityContext(member);

        Jwt jwt = commonService.getJWT(member.getIdentifyNo(), member.getAuthorities());
        session.setAttribute("accessToken", jwt.getAccessToken());
        session.setAttribute("refreshToken", jwt.getRefreshToken());

        // TODO : testcase 추가, auth서버에서도 해당 로직을 추가로 보내줘야함
        session.setAttribute("accessTokenExpireDateTime", jwt.getAccessTokenExpireDateTime());
        session.setAttribute("refreshTokenExpireDateTime", jwt.getRefreshTokenExpireDateTime());
        return "all";
    }
}

package shop.gaship.gashipfront.security.social.controller;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import shop.gaship.gashipfront.security.social.dto.domain.Member;
import shop.gaship.gashipfront.security.social.dto.accesstoken.NaverAccessToken;
import shop.gaship.gashipfront.security.social.dto.jwt.JwtTokenDto;
import shop.gaship.gashipfront.security.social.dto.userdata.NaverUserData;
import shop.gaship.gashipfront.security.social.service.common.CommonService;
import shop.gaship.gashipfront.security.social.service.dance.NaverLoginService;

@Controller
@RequestMapping("/securities")
@RequiredArgsConstructor
@Slf4j
public class OAuthController {
    private final NaverLoginService naverLoginService;
    private final CommonService commonService;

    @GetMapping("/login/naver")
    @ResponseBody
    public ResponseEntity<String> redirectUriForLoginPageRequestNaver(HttpSession session) throws UnsupportedEncodingException {
        HttpHeaders headers = new HttpHeaders();
        String uriForLoginPageRequest = naverLoginService.getUriForLoginPageRequest(session);
        headers.setLocation(URI.create(uriForLoginPageRequest));
        log.debug("uriForLoginPageRequest : {}", uriForLoginPageRequest);

        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @GetMapping(value = "/login/naver/callback", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAccessTokenAndAuthenticateNaver(String code, String parameterState, HttpSession session) throws Exception {
        String redisState = (String) session.getAttribute("state");
        NaverAccessToken naverAccessToken = naverLoginService.getAccessToken(code, parameterState, redisState);

        NaverUserData data = naverLoginService.getUserDataThroughAccessToken(naverAccessToken.getAccessToken());
        Member member = commonService.getMember(data.getResponse().getEmail());
        naverLoginService.setSecurityContext(member);

        // TODO 1 : jwt 요청코드
        JwtTokenDto jwt = commonService.getJWT(member.getIdentifyNo(), member.getAuthorities());
        session.setAttribute("accessToken", jwt.getAccessToken());
        session.setAttribute("refreshToken", jwt.getRefreshToken());
        return "/all";
    }
}

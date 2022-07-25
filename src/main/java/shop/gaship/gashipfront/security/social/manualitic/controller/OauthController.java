package shop.gaship.gashipfront.security.social.manualitic.controller;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import shop.gaship.gashipfront.security.common.gashipauth.service.AuthApiService;
import shop.gaship.gashipfront.security.social.manualitic.dto.userdata.NaverUserData;
import shop.gaship.gashipfront.security.common.member.dto.MemberDto;
import shop.gaship.gashipfront.security.social.manualitic.dto.NaverAccessToken;
import shop.gaship.gashipfront.security.common.dto.JwtDto;
import shop.gaship.gashipfront.security.social.manualitic.service.NaverLoginService;
import shop.gaship.gashipfront.security.common.util.SignupManager;
import shop.gaship.gashipfront.security.social.util.SecurityContextLoginManager;

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
    private final AuthApiService authAPIService;
    private final SignupManager signupManager;

    /**
     * Naver로 로그인요청을 보낼 uri를 요청측에 반환해주는 기능입니다.
     *
     * @param session naverLoginService에서 session을 통해 state값을 저장하기위해서 매개변수로 사용합니다.
     * @return 전체 uri값을 헤더에 담은뒤 ResponseEntity를 반환합니다.
     * @throws UnsupportedEncodingException URLEncoder.encode 를 하는 과정에서 발생할수 있는 예외입니다.
     * @author 최겸준
     */
    @GetMapping("/login/naver")
    @ResponseBody
    public ResponseEntity<String> redirectUriForLoginPageRequestNaver(HttpSession session)
        throws UnsupportedEncodingException, URISyntaxException {
        HttpHeaders headers = new HttpHeaders();
        String uriForLoginPageRequest = naverLoginService.getUriForLoginPageRequest();
        String[] stateSplit = uriForLoginPageRequest.split("&state=");
        session.setAttribute("state", stateSplit[1]);

        headers.setLocation(URI.create(uriForLoginPageRequest));
        log.debug("uriForLoginPageRequest : {}", uriForLoginPageRequest);

        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    /**
     * accessToken, naverUserDate를 가져와서 로그인 시키는 기능입니다.
     *
     * @param code accesstoken을 발급받기위한 인가코드입니다.
     * @param paramState csrf 공격을 막기위해 비교값으로 전달되는 값입니다.
     * @param session 기존에 저장된 session 영역의 state값을 가져오는 용도로 사용합니다.
     * @return view name입니다.
     */
    @GetMapping("/login/naver/callback")
    public String getAccessTokenAndAuthenticateNaver(String code, @RequestParam(value = "state") String paramState, HttpSession session) {
        String redisState = (String) session.getAttribute("state");
        NaverAccessToken naverAccessToken = naverLoginService.getAccessToken(code, paramState, redisState);

        NaverUserData data = naverLoginService.getUserDataThroughAccessToken(naverAccessToken.getAccessToken());
        MemberDto member = signupManager.getMember(data.getResponse());

        SecurityContextLoginManager.setSecurityContext(member);
        JwtDto jwt = authAPIService.getJwt(member.getMemberNo(), member.getAuthorities());
        session.setAttribute("jwt", jwt);
        return "all";
    }
}

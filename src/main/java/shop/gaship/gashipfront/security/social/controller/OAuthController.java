package shop.gaship.gashipfront.security.social.controller;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipfront.security.social.dto.Member;
import shop.gaship.gashipfront.security.social.dto.NaverAccessToken;
import shop.gaship.gashipfront.security.social.dto.NaverUserData;
import shop.gaship.gashipfront.security.social.service.LoginService;

@RestController
@RequestMapping("/securities")
@RequiredArgsConstructor
@Slf4j
public class OAuthController {
    @Qualifier("naverLoginServiceImpl")
    private final LoginService naverLoginServiceImpl;

    @Qualifier("paycoLoginServiceImpl")
    private final LoginService paycoLoginServiceImpl;

    @GetMapping("/login/naver")
    public ResponseEntity<String> redirectUriForLoginPageRequest(HttpServletResponse response) throws UnsupportedEncodingException {
        HttpHeaders headers = new HttpHeaders();
        String uriForLoginPageRequest = naverLoginServiceImpl.getUriForLoginPageRequest();
        headers.setLocation(URI.create(uriForLoginPageRequest));
        log.debug("uriForLoginPageRequest : {}", uriForLoginPageRequest);

        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @GetMapping(value = "/login/naver/callback", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAccessTokenAndAuthenticate(String code, String state) throws Exception {
        NaverAccessToken naverAccessToken = naverLoginServiceImpl.getAccessToken(code, state);
        NaverUserData data = naverLoginServiceImpl.getUserDataThroughAccessToken(naverAccessToken.getAccessToken());
        Member member = naverLoginServiceImpl.getMember(data.getMobile());

        // TODO 5 : 필요한가?
//        SecurityContext context = SecurityContextHolder.getContext();
//        Authentication authentication = new UsernamePasswordAuthenticationToken();
//        context.setAuthentication();
        return "{\"Authorization\" : \"" + naverLoginServiceImpl.requestJWT(data.getId(), member.getEmail()) + "\"";
    }
}

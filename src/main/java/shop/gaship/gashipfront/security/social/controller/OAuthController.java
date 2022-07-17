package shop.gaship.gashipfront.security.social.controller;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.swing.text.html.parser.Entity;
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
import shop.gaship.gashipfront.security.social.exception.ResponseEntityBodyIsErrorResponseException;
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

    @GetMapping("/login/naver/callback")
    public String getAccessTokenAndAuthenticateNaver(String code, String parameterState, HttpSession session) throws Exception {
        String redisState = (String) session.getAttribute("state");
        NaverAccessToken naverAccessToken = naverLoginService.getAccessToken(code, parameterState, redisState);

        NaverUserData data = naverLoginService.getUserDataThroughAccessToken(naverAccessToken.getAccessToken());

        Optional<Member> optionalMember = getOptionalMember(session, data);
        if (!optionalMember.isPresent()) return "redirect:signup"; // 회원가입 url http://localhost:8080/signup

        Member member = optionalMember.get();
        naverLoginService.setSecurityContext(member);

        JwtTokenDto jwt = commonService.getJWT(member.getIdentifyNo(), member.getAuthorities());
        session.setAttribute("accessToken", jwt.getAccessToken());
        session.setAttribute("refreshToken", jwt.getRefreshToken());
        return "all";
    }

    private Optional<Member> getOptionalMember(HttpSession session, NaverUserData data) {
        Member member;
        try {
            member = commonService.getMemberByEmail(data.getResponse().getEmail());
        } catch (ResponseEntityBodyIsErrorResponseException e) {
            if (e.getStatusCode().equals(HttpStatus.NO_CONTENT)) {
                session.setAttribute("email", data.getResponse().getEmail()); // 회원가입 폼에서 자동으로 입력될 email정보
                return Optional.empty();
            }
            throw e;
        }

        return Optional.of(member);
    }
}

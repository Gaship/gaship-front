package shop.gaship.gashipfront.security.social.manualitic.controller;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import shop.gaship.gashipfront.cart.service.CartService;
import shop.gaship.gashipfront.member.dto.MemberAllFieldDto;
import shop.gaship.gashipfront.security.basic.dto.TokenRequestDto;
import shop.gaship.gashipfront.security.common.dto.JwtDto;
import shop.gaship.gashipfront.security.common.gashipauth.service.AuthApiService;
import shop.gaship.gashipfront.security.common.util.SignupManager;
import shop.gaship.gashipfront.security.social.manualitic.dto.NaverAccessToken;
import shop.gaship.gashipfront.security.social.manualitic.dto.userdata.NaverUserData;
import shop.gaship.gashipfront.security.social.manualitic.service.NaverLoginService;
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
    private final AuthApiService authApiService;
    private final SignupManager signupManager;
    private final CartService cartService;
    private static final String CART_ID = "CID";

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
    public String getAccessTokenAndAuthenticateNaver(String code,
                                                     @RequestParam(value = "state")
                                                     String paramState,
                                                     HttpSession session,
                                                     HttpServletRequest request,
                                                     HttpServletResponse response) {
        String redisState = (String) session.getAttribute("state");
        NaverAccessToken naverAccessToken =
            naverLoginService.getAccessToken(code, paramState, redisState);
        NaverUserData data =
            naverLoginService.getUserDataThroughAccessToken(naverAccessToken.getAccessToken());
        MemberAllFieldDto member = signupManager.getMember(data.getResponse());

        TokenRequestDto tokenRequestDto =
            new TokenRequestDto(
                member.getMemberNo(),
                member.getName(),
                member.getAuthorities()
            );

        session.setAttribute("memberInfo", tokenRequestDto);

        SecurityContextLoginManager.setSecurityContext(member);
        JwtDto jwt = authApiService.getJwt(member.getMemberNo(), member.getAuthorities());
        session.setAttribute("jwt", jwt);

        // 비회원일 떄 쓰던 장바구니 쿠키 값을 찾아서
        Optional<Cookie> nonMemberCookie = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(CART_ID))
                .findFirst();
        // 찾은 쿠키값이 존재하면 비회원 때 담은 상품들을 회원의 장바구니에 넣는다.
        nonMemberCookie.ifPresent(cookie -> cartService.mergeCart(cookie.getValue(), member.getMemberNo()));
        //장바구니의 쿠키값을 회원의 id 로 바꿔준다.
        Cookie cookie = new Cookie(CART_ID, member.getMemberNo().toString());
        cookie.setMaxAge(60 * 60 * 24 * 100);
        response.addCookie(cookie);
        return "all";
    }
}

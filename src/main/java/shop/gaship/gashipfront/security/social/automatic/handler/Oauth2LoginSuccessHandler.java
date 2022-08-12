package shop.gaship.gashipfront.security.social.automatic.handler;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import shop.gaship.gashipfront.cart.service.CartService;
import shop.gaship.gashipfront.member.dto.MemberAllFieldDto;
import shop.gaship.gashipfront.member.dto.MemberAllFieldDto;
import shop.gaship.gashipfront.security.common.dto.JwtDto;
import shop.gaship.gashipfront.security.common.dto.UserDetailsDto;
import shop.gaship.gashipfront.security.common.gashipauth.service.AuthApiService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Optional;

/**
 * oauth2 기능을 통한 로그인 성공시에 기본적인 처리 및 jwt를 요청하고 session에 추가해주기 위한 클래스입니다.
 *
 * @author : 최겸준
 * @see SavedRequestAwareAuthenticationSuccessHandler
 * @since 1.0
 */

@RequiredArgsConstructor
public class Oauth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final AuthApiService commonService;
    private final CartService cartService;
    private static final String NON_MEMBER_CART_ID = "NONMEMBERCARTID";
    private static final String MEMBER_CART_ID = "MEMBERCARTID";

    /**
     * 로그인 성공시 jwt 토큰을 생성하고 session 또는 redis session에 넣어주는 기능입니다.
     *
     * @author 최겸준
     */
    @SneakyThrows
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        super.onAuthenticationSuccess(request, response, authentication);

        UserDetailsDto memberDto = (UserDetailsDto) authentication.getPrincipal();
        MemberAllFieldDto member = memberDto.getMember();

        JwtDto jwt = commonService.getJwt(member.getMemberNo(), member.getAuthorities());
        HttpSession session = request.getSession();
        session.setAttribute("jwt", jwt);

        // 비회원일 떄 쓰던 장바구니 쿠키 값을 찾아서
        Optional<Cookie> nonMemberCookie = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(NON_MEMBER_CART_ID))
                .findFirst();
        // 찾은 쿠키값이 존재하면 비회원 때 담은 상품들을 회원의 장바구니에 넣는다.
        nonMemberCookie.ifPresent(cookie -> cartService.mergeCart(cookie.getValue(), member.getMemberNo()));
        // 비회원때 쓰던 장바구니 쿠키를 없앤다.
        Cookie killCookie = new Cookie(NON_MEMBER_CART_ID, null);
        killCookie.setMaxAge(0);
        response.addCookie(killCookie);
        //회원에게 장바구니 쿠키를 지급한다.
        response.addCookie(new Cookie(MEMBER_CART_ID, member.getMemberNo().toString()));
    }
}

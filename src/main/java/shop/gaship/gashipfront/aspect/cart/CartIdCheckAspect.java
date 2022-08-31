package shop.gaship.gashipfront.aspect.cart;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import shop.gaship.gashipfront.cart.service.CartService;
import shop.gaship.gashipfront.security.basic.dto.SignInUserDetailsDto;
import shop.gaship.gashipfront.security.common.dto.UserDetailsDto;

/**
 * @author 최정우
 * @since 1.0
 */

@Component
@Aspect
@Slf4j
@RequiredArgsConstructor
public class CartIdCheckAspect {

    public static final String CART_ID = "CID";
    private static String cartKey;
    private final CartService cartService;

    @Before("execution(* shop.gaship.gashipfront.cart.controller..*(..))")
    public void getCatId() throws Throwable {
        String userId = null;

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal != "anonymousUser"){
            UserDetailsDto userDetailsDto = (UserDetailsDto) principal;
            userId = userDetailsDto.getMemberNo().toString();
        }

        HttpServletRequest request =
            ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response =
            ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
//      request 에서 비회원 카트 아이디 조회
        Optional<Cookie> nonMemberCookie = Arrays.stream(request.getCookies())
                                                 .filter(cookie -> cookie.getName().equals(CART_ID))
                                                 .findFirst();
//      세션에도 회원 정보(회원 장바구니 식별값)가 없고 쿠키에도 catId(비회원 장바구니 식별값) 가 없을 경우
        if (Objects.isNull(userId) && nonMemberCookie.isEmpty()) {
            // 비회원 장바구니 쿠키를 부여한다.
            cartKey = UUID.randomUUID().toString();
            Cookie cookie = new Cookie(CART_ID, cartKey);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setMaxAge(60 * 60 * 24 * 100);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        //비회원이 장바구니를 이용할 때마다 maxAge 늘림
        if (Objects.isNull(userId) && nonMemberCookie.isPresent()) {

            Cookie refreshAgeCookie = nonMemberCookie.get();
            refreshAgeCookie.setMaxAge(60 * 60 * 24 * 100);
            refreshAgeCookie.setPath("/");
            response.addCookie(refreshAgeCookie);
        }
//        비로그인에 장바구니 쿠키가 있는 경우
        nonMemberCookie.ifPresent(cookie -> cartKey = cookie.getValue());
//세션에 회원정보가 있으면 (로그인 되어있으면) cartKey 를 회원 id 값으로 설정
        if (Objects.nonNull(userId)) {
            cartKey = userId;
        }
        //장바구니를 조작할 때 쿠키와 세션 아이디가 있으면(로그인이 되어있으면) 비회원 쿠키 삭제하고 비회원 장바구니 상품을 회원 장바구니에 추가
        if (Objects.nonNull(userId) && nonMemberCookie.isPresent()) {
            cartService.mergeCart(nonMemberCookie.get().getValue(), Integer.valueOf(userId));
            Cookie afterMergeKillCookie = new Cookie(CART_ID, null);
            afterMergeKillCookie.setMaxAge(0);
            response.addCookie(afterMergeKillCookie);
        }
        // 도출해낸 cartKey 를 치환시키고 컨트롤러 실행
        request.setAttribute(CART_ID, cartKey);
    }
}

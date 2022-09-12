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
    public static final String ANONYMOUS_USER = "anonymousUser";
    private final CartService cartService;
    private static String userId;

    /**
     * 회원 유형에 따라
     * @throws Throwable
     */
    @Before("execution(* shop.gaship.gashipfront.cart.controller..*(..))")
    public void getCatId() throws Throwable {
        HttpServletRequest request =
            ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response =
            ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

        Object principal = getPrincipal();
        //request 에서 비회원 카트 아이디 조회(없을수도 있음)
        Optional<Cookie> nonMemberCookie = getNonMemberCookie(request);
        //회원의 유형을 얻는다(회원여부,쿠키여부 총 네가지)
        UserType userType = getUserType(principal, nonMemberCookie);
        if (userType == UserType.MEMBER){
            UserDetailsDto userDetailsDto = (UserDetailsDto) principal;
            userId = userDetailsDto.getMemberNo().toString();
        } else if(userType == UserType.MEMBER_WITH_COOKIE){
            UserDetailsDto userDetailsDto = (UserDetailsDto) principal;
            userId = userDetailsDto.getMemberNo().toString();
            cartService.mergeCart(nonMemberCookie.get().getValue(), userId);
            killCookie(response);
        } else if(userType == UserType.NONMEMBER){
            userId  = setNonMemberCookie(response);
        } else if(userType == UserType.NONMEMBER_WITH_COOKIE){
            refreshNonMemberCookie(response, nonMemberCookie);
            userId = nonMemberCookie.get().getValue();
        } else {
            return;
        }
        // 도출해낸 cartKey 를 치환시키고 컨트롤러 실행
        request.setAttribute(CART_ID, userId);
    }

    private UserType getUserType(Object principal, Optional<Cookie> nonMemberCookie) {
        if(principal != ANONYMOUS_USER && nonMemberCookie.isEmpty()){
            return UserType.MEMBER;
        } else if(principal != ANONYMOUS_USER && nonMemberCookie.isPresent()){
            return  UserType.MEMBER_WITH_COOKIE;
        } else if(principal == ANONYMOUS_USER && nonMemberCookie.isEmpty()){
            return  UserType.NONMEMBER;
        } else if(principal == ANONYMOUS_USER && nonMemberCookie.isPresent()){
            return  UserType.NONMEMBER_WITH_COOKIE;
        }
        return null;
    }

    private void refreshNonMemberCookie(HttpServletResponse response, Optional<Cookie> nonMemberCookie) {
        Cookie refreshAgeCookie = nonMemberCookie.get();
        refreshAgeCookie.setMaxAge(60 * 60 * 24 * 7);
        refreshAgeCookie.setPath("/carts");
        response.addCookie(refreshAgeCookie);
    }

    private String setNonMemberCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(CART_ID, UUID.randomUUID().toString());
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(60 * 60 * 24 * 7);
        cookie.setPath("/carts");
        response.addCookie(cookie);
        return cookie.getValue();
    }

    private Optional<Cookie> getNonMemberCookie(HttpServletRequest request) {
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(CART_ID))
                .findFirst();
    }

    private Object getPrincipal() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private void killCookie(HttpServletResponse response) {
        Cookie afterMergeKillCookie = new Cookie(CART_ID, null);
        afterMergeKillCookie.setPath("/carts");
        afterMergeKillCookie.setMaxAge(0);
        response.addCookie(afterMergeKillCookie);
    }
}

package shop.gaship.gashipfront.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import shop.gaship.gashipfront.cart.service.CartService;
import shop.gaship.gashipfront.security.basic.dto.TokenRequestDto;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

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

    @Around("execution(* shop.gaship.gashipfront.cart.controller..*(..))")
    public Object getCatId(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
//      세션에서 회원 id 조회
        TokenRequestDto memberInfo = (TokenRequestDto) request.getSession().getAttribute("memberInfo");
//      request 에서 비회원 카트 아이디 조회
        Optional<Cookie> nonMemberCookie = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(CART_ID))
                .findFirst();
//      세션에도 회원 정보(회원 장바구니 식별값)가 없고 쿠키에도 catId(비회원 장바구니 식별값) 가 없을 경우
        if( Objects.isNull(memberInfo) && nonMemberCookie.isEmpty()){
            // 비회원 장바구니 쿠키를 부여한다.
            cartKey = UUID.randomUUID().toString();
            Cookie cookie = new Cookie(CART_ID, cartKey);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setMaxAge(60 * 60 * 24 * 100);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
//        비로그인에 장바구니 쿠키가 있는 경우
        nonMemberCookie.ifPresent(cookie -> cartKey = cookie.getValue());
//세션에 회원정보가 있으면 (로그인 되어있으면) cartKey 를 회원 id 값으로 설정
        if(Objects.nonNull(memberInfo)){
            cartKey = String.valueOf(memberInfo.getMemberNo());
        }
        // 도출해낸 cartKey 를 치환시키고 컨트롤러 실행
        request.setAttribute(CART_ID,cartKey);
        return joinPoint.proceed();
    }
//
//    @AfterReturning("execution(* shop.gaship.gashipfront.security.basic.handler..*(..))")
//    public Object getCatId() throws Throwable {
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
//
//
//        // 비회원일 때 쓰던 장바구니 쿠키 값을 찾아서
//        Optional<Cookie> nonMemberCookie = Arrays.stream(request.getCookies())
//                .filter(cookie -> cookie.getName().equals(CART_ID))
//                .findFirst();
//        // 찾은 쿠키값이 존재하면 비회원 때 담은 상품들을 회원의 장바구니에 넣는다.
//        TokenRequestDto memberInfo = (TokenRequestDto) request.getSession().getAttribute("memberInfo");
//        nonMemberCookie.ifPresent(cookie -> cartService.mergeCart(cookie.getValue(), memberInfo.getMemberNo()));
//        response.sendRedirect("/");
//    }
}

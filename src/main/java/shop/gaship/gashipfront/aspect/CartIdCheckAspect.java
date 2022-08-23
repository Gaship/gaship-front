package shop.gaship.gashipfront.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
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
public class CartIdCheckAspect {
    public static final String CART_ID = "CID";
    @Around("execution(* shop.gaship.gashipfront.cart.controller..*(..))")
    public Object getCatId(ProceedingJoinPoint joinPoint) throws Throwable {
        String cartKey;
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//         로그인 한 회원이면 세션의 id 를 통해 장바구니 번호 부여
        TokenRequestDto memberInfo = (TokenRequestDto) request.getSession().getAttribute("memberInfo");
//        if(Objects.nonNull(memberInfo)){
//            return;
//        }
        // 비로그인인데 장바구니 쿠키가 있는 사람이면 쿠키 value로 장바구니 부여
        Optional<Cookie> cartCookie = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(CART_ID))
                .findFirst();
        if( Objects.nonNull(cartCookie)){
            log.info("카트쿠키는 = {}",cartCookie);
        }
//        // 비로그인에 장바구니 쿠키가 없는 사람이면 쿠키 부여 및 장바구니 부여
//        String newCartId = UUID.randomUUID().toString();
//        response.setCookie(CART_ID, newCartId);
//        return this;
        log.info("호출 테스트");
        return joinPoint.proceed();
    }
}

package shop.gaship.gashipfront.interceptor;

import java.util.Arrays;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 요청 마다 세션, 세션 쿠키 타임아웃 초기화 시키기 위한 인터셉터.
 *
 * @author : 조재철
 * @since 1.0
 */
@Component
public class SessionInterceptor implements HandlerInterceptor {

    public static final String SESSION_ID = "GASHIP_SESSIONID";
    public static final Integer SESSION_AND_COOKIE_TIME_OUT_SECOND = 60 * 60 * 24;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            Arrays.stream(cookies)
                  .filter(cookie -> cookie.getName().equals(SESSION_ID))
                  .findFirst()
                  .ifPresent(cookie -> cookie.setMaxAge(SESSION_AND_COOKIE_TIME_OUT_SECOND));
        }

        request.getSession().setMaxInactiveInterval(SESSION_AND_COOKIE_TIME_OUT_SECOND);

        return true;
    }
}

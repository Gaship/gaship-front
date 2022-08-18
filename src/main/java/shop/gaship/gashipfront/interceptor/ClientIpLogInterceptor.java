package shop.gaship.gashipfront.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 어떤 클라이언트로부터 요청이 왔는지 IP를 얻어내는 인터셉터입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Slf4j
public class ClientIpLogInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        String clientIp = request.getHeader("X-FORWARDED-FOR");
        log.info("REQUEST URI - {} : {}", request.getRequestURI(), clientIp);

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}

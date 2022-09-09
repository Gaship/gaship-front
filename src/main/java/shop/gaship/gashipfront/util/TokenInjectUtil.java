package shop.gaship.gashipfront.util;

import java.util.Objects;
import java.util.function.Consumer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import shop.gaship.gashipfront.security.common.dto.JwtDto;

/**
 * @author : 조재철
 * @since 1.0
 */
@Component
public class TokenInjectUtil {
    public Consumer<HttpHeaders> headersConsumer() {
        ServletRequestAttributes requestAttributes =
            (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest req = Objects.requireNonNull(requestAttributes).getRequest();
        HttpSession session = req.getSession(true);
        JwtDto jwt = (JwtDto) session.getAttribute("jwt");

        return httpHeaders -> httpHeaders.add("X-AUTH-TOKEN", jwt.getAccessToken());
    }
}

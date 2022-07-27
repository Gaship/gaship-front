package shop.gaship.gashipfront.security.repository;

import java.util.Objects;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Repository;

/**
 * CSRF 토큰을 저장, 관리하기위한 클래스입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Repository
public class RedisCsrfRepository implements CsrfTokenRepository {
    public static final String PARAMETER_NAME = "_csrf";
    public static final String HEADER_NAME = "X-CSRF-TOKEN";
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisCsrfRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public CsrfToken generateToken(HttpServletRequest request) {
        return new DefaultCsrfToken(HEADER_NAME, PARAMETER_NAME, csrfTokenGenerate());
    }

    @Override
    public void saveToken(CsrfToken token, HttpServletRequest request,
                          HttpServletResponse response) {
        HttpSession session = request.getSession(false);

        if(Objects.nonNull(session)){
            redisTemplate.opsForValue().set(session.getId(), generateToken(request));
        }
    }

    @Override
    public CsrfToken loadToken(HttpServletRequest request) {
        String sessionId = request.getSession().getId();
        byte[] tokenString = (byte[]) redisTemplate.opsForValue().get(sessionId);
        if(Objects.nonNull(tokenString)){
            return SerializationUtils.deserialize(tokenString);
        }
        return null;
    }

    private String csrfTokenGenerate() {
        return UUID.randomUUID().toString();
    }
}

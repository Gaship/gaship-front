package shop.gaship.gashipfront.security.social.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author : 최겸준
 * @since 1.0
 */
@SpringBootTest
@EnableRedisHttpSession
public class RedisConfigTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @DisplayName("레디스에 저장 및 조회가 가능하다.")
    @Test
    void saveAndGet() {
        redisTemplate.opsForHash().put("1번방", "햄토리", "안녕하세요");
        String result = (String) redisTemplate.opsForHash().get("1번방", "햄토리");

        assertThat(result)
            .isEqualTo("안녕하세요");
    }

    @Test
    void session() {

    }
}

package shop.gaship.gashipfront.security.social.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipfront.config.RedisConfig;
import shop.gaship.gashipfront.config.SecureManagerConfig;

/**
 * @author : 최겸준
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(value = {RedisConfig.class, SecureManagerConfig.class})
@TestPropertySource("classpath:application.properties")
class RedisConfigTest {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @DisplayName("레디스에 저장 및 조회가 가능하다.")
    @Test
    void saveAndGet() {
        redisTemplate.opsForHash().put("1번방", "햄토리", "안녕하세요");
        String result = (String) redisTemplate.opsForHash().get("1번방", "햄토리");

        assertThat(result)
            .isEqualTo("안녕하세요");
    }
}

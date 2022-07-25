package shop.gaship.gashipfront.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * 설명작성란
 *
 * @author 김민수
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(value = {ServerConfig.class})
@TestPropertySource(value = {"classpath:application.properties", "classpath:application-dev.properties"})
class ServerConfigTest {
    @Autowired
    ServerConfig config;

    @Test
    void serverConfigTest() {
        assertThat(config.getGatewayUrl())
            .isEqualTo("http://localhost:9090");
    }
}

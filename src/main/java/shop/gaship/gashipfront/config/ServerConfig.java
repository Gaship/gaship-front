package shop.gaship.gashipfront.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 프론트 서버 운영에 대한 전반적인 설정입니다.
 *
 * @author : 김민수
 * @since 1.0
 */
@Configuration
public class ServerConfig {
    @Value("${gaship.server.gateway-url}")
    private String gatewayBaseUrl;

    @Bean
    public String gatewayBaseUrl() {
        return this.gatewayBaseUrl;
    }

}

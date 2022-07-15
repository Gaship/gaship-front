package shop.gaship.gashipfront.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * packageName    : shop.gaship.gashipfront.config
 * fileName       : ServerConfig
 * author         : choijungwoo
 * date           : 2022/07/15
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/15        choijungwoo       최초 생성
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

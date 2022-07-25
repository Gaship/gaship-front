package shop.gaship.gashipfront.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 프론트 서버 운영에 대한 전반적인 설정입니다.
 *
 * @author : 김민수
 * @since 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "gaship-server")
public class ServerConfig {
    private String gatewayUrl;

    public String getGatewayUrl() {
        return gatewayUrl;
    }

    public void setGatewayUrl(String gatewayUrl) {
        this.gatewayUrl = gatewayUrl;
    }
}

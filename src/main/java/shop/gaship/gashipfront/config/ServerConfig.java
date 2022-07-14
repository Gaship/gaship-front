package shop.gaship.gashipfront.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * packageName    : shop.gaship.gashipfront.config <br/>
 * fileName       : ServerConfig <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/14 <br/>
 * description    : <br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/14           김민수               최초 생성                         <br/>
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

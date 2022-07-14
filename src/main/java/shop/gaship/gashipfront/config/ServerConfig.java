package shop.gaship.gashipfront.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * packageName    : shop.gaship.gashipfront.config <br/>
 * fileName       : ServerConfig <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/14 <br/>
 * description    : 프론트 서버 운영에 대한 전반적인 설정입니다./<br/>
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

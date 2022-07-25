package shop.gaship.gashipfront.config;

import java.time.Duration;
import java.util.Objects;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipfront.exceptions.NoResponseDataException;
import shop.gaship.gashipfront.util.dto.SecureKeyResponse;

/**
 * NHN Secure Manager를 통해 보호하고있는 데이터를 제공하도록 도와주는 클래스입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Configuration
@ConfigurationProperties("secure-key-manager")
public class SecureManagerConfig {
    private String url;
    private String appKey;

    String findSecretDataFromSecureKeyManager(String keyId) {
        String errorMessage = "응답 결과가 없습니다.";
        return Objects.requireNonNull(WebClient.create(url).get()
                .uri("/keymanager/v1.0/appkey/{appkey}/secrets/{keyid}", appKey, keyId)
                .retrieve()
                .toEntity(SecureKeyResponse.class)
                .timeout(Duration.ofSeconds(20))
                .blockOptional()
                .orElseThrow(() -> new NoResponseDataException(errorMessage))
                .getBody())
            .getBody()
            .getSecret();
    }

    public String getUrl() {
        return url;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }
}

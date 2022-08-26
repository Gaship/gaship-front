package shop.gaship.gashipfront.config;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : 최겸준
 * @since 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "oauth")
@RequiredArgsConstructor
@Getter
@Setter
public class OauthConfig {
    private final SecureManagerConfig secureManagerConfig;
    private String naverClientId;
    private String naverClientSecret;
    private String naverRedirectUrl;
    private String naverApiUrlLogin;
    private String naverApiUrlAccesstoken;
    private String naverApiUrlUserData;

    @Bean
    public InitializingBean oauthConfigInitializer() {
        return this::setSecret;
    }

    public void setSecret()
        throws UnrecoverableKeyException, CertificateException, NoSuchAlgorithmException,
        KeyStoreException, IOException, KeyManagementException {
        this.naverClientId =
            secureManagerConfig.findSecretDataFromSecureKeyManager(this.naverClientId);
        this.naverClientSecret =
            secureManagerConfig.findSecretDataFromSecureKeyManager(this.naverClientSecret);
    }
}

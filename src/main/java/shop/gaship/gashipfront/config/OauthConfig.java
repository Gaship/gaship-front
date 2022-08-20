package shop.gaship.gashipfront.config;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 *
 *
 * @author : 최겸준
 * @since 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "oauth")
@NoArgsConstructor
@Getter
@Setter
public class OauthConfig {

    private String naverClientId;
    private String naverClientSecret;
    private String naverRedirectUrl;
    private String naverApiUrlLogin;
    private String naverApiUrlAcesstoken;
    private String naverApiUrlUserData;

    public OauthConfig(SecureManagerConfig secureManagerConfig)
        throws UnrecoverableKeyException, CertificateException, NoSuchAlgorithmException,
        KeyStoreException, IOException, KeyManagementException {

        this.naverClientId = secureManagerConfig.findSecretDataFromSecureKeyManager(this.naverClientId);
        this.naverClientSecret = secureManagerConfig.findSecretDataFromSecureKeyManager(this.naverClientSecret);
    }
}

package shop.gaship.gashipfront.config;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import shop.gaship.gashipfront.util.dto.SecureKeyResponse;

/**
 * NHN Secure Manager를 통해 보호하고있는 데이터를 제공하도록 도와주는 클래스입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Configuration
@ConfigurationProperties("secure-key-manager")
@Slf4j
public class SecureManagerConfig {
    private String url;
    private String appKey;
    private String localKey;

    String findSecretDataFromSecureKeyManager(String keyId)
        throws CertificateException, NoSuchAlgorithmException, KeyStoreException,
        UnrecoverableKeyException, IOException, KeyManagementException {
        KeyStore clientStore = KeyStore.getInstance("PKCS12");
        clientStore.load(
            new ClassPathResource("github-action.p12").getInputStream(),
            localKey.toCharArray());

        SSLContextBuilder sslContextBuilder = new SSLContextBuilder();
        sslContextBuilder.setProtocol("TLS");
        sslContextBuilder.loadKeyMaterial(clientStore, localKey.toCharArray());
        sslContextBuilder.loadTrustMaterial(new TrustSelfSignedStrategy());

        SSLConnectionSocketFactory sslConnectionSocketFactory =
            new SSLConnectionSocketFactory(sslContextBuilder.build());
        CloseableHttpClient httpClient = HttpClients.custom()
            .setSSLSocketFactory(sslConnectionSocketFactory)
            .build();
        HttpComponentsClientHttpRequestFactory requestFactory =
            new HttpComponentsClientHttpRequestFactory(httpClient);

        return Objects.requireNonNull(new RestTemplate(requestFactory)
                .getForEntity(url + "/keymanager/v1.0/appkey/{appkey}/secrets/{keyid}",
                    SecureKeyResponse.class, appKey, keyId)
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

    public void setLocalKey(String localKey) {
        this.localKey = localKey;
    }
}

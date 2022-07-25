package shop.gaship.gashipfront.config;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipfront.util.dto.SecureKeyResponse;

/**
 * 설명작성란
 *
 * @author 김민수
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(value = {SecureManagerConfig.class})
@TestPropertySource("classpath:application.properties")
class SecureManagerConfigTest {
    @Autowired
    SecureManagerConfig config;

    @Test
    void secureManagerConfigTest() {
        assertThat(config.getUrl())
            .isEqualTo("https://api-keymanager.cloud.toast.com");
        assertThat(config.getAppKey())
            .isEqualTo("mcXcTmrnbRezlila");

    }

    @Test
    void findP12Data()
        throws UnrecoverableKeyException, CertificateException, NoSuchAlgorithmException,
        KeyStoreException, IOException, KeyManagementException {

        String result = config.findSecretDataFromSecureKeyManager("a93c53427dd84868bdc3402d94270bf4");

        assertThat(result)
            .isEqualTo("133.186.211.156");
    }
}

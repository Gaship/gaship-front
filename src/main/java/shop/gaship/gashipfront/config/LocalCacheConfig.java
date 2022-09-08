package shop.gaship.gashipfront.config;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import javax.cache.Caching;
import lombok.RequiredArgsConstructor;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import shop.gaship.gashipfront.exceptions.NotFoundDataProtectionReposeData;

/**
 * cache config.
 *
 * @author : 김보민
 * @since 1.0
 */
@EnableCaching
@Configuration
@RequiredArgsConstructor
public class LocalCacheConfig {
    @Value("${cache.redis.database}")
    private int cacheRedisDatabase;

    public static final String CATEGORY_CACHE = "category";
    public static final String PRODUCT_CACHE = "product";
    private final RedisConfig redisConfig;
    private final SecureManagerConfig secureManagerConfig;

    @Bean
    @Primary
    public CacheManager jCacheCacheManager() {
        javax.cache.CacheManager cacheManager = Caching.getCachingProvider().getCacheManager();

        CacheConfigurationBuilder<Object, Object> configuration =
                CacheConfigurationBuilder.newCacheConfigurationBuilder(
                                Object.class,
                                Object.class,
                                ResourcePoolsBuilder
                                        .newResourcePoolsBuilder().offheap(1, MemoryUnit.MB))
                        .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(180)));

        javax.cache.configuration.Configuration<Object, Object> ehcacheCacheConfiguration =
                Eh107Configuration.fromEhcacheCacheConfiguration(configuration);

        cacheManager.createCache(CATEGORY_CACHE, ehcacheCacheConfiguration);
        return new JCacheCacheManager(cacheManager);
    }

    @Bean
    public CacheManager redisCacheManager() {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration
                .defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext
                        .SerializationPair
                        .fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext
                        .SerializationPair
                        .fromSerializer(new GenericJackson2JsonRedisSerializer()));

        Map<String, RedisCacheConfiguration> cacheConfiguration = new HashMap<>();
        cacheConfiguration.put(PRODUCT_CACHE, redisCacheConfiguration.entryTtl(Duration.ofDays(100)));

        return RedisCacheManager.builder(cacheRedisConnectionFactory())
                .cacheDefaults(redisCacheConfiguration)
                .withInitialCacheConfigurations(cacheConfiguration)
                .build();
    }

    @Bean
    public RedisConnectionFactory cacheRedisConnectionFactory() {
        String secretHost;
        String secretPassword;
        try {
            secretHost = secureManagerConfig.findSecretDataFromSecureKeyManager(redisConfig.getHost());
            secretPassword = secureManagerConfig.findSecretDataFromSecureKeyManager(redisConfig.getPassword());
        } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException
                | UnrecoverableKeyException | IOException | KeyManagementException e) {
            throw new NotFoundDataProtectionReposeData("Secure Manager Error");
        }

        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(secretHost);
        configuration.setPort(redisConfig.getPort());
        configuration.setPassword(secretPassword);
        configuration.setDatabase(cacheRedisDatabase);

        return new LettuceConnectionFactory(configuration);
    }
}

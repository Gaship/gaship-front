package shop.gaship.gashipfront.config;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * cache config.
 *
 * @author : 김보민
 * @since 1.0
 */
@EnableCaching
@Configuration
public class LocalCacheConfig {
    public static final String CATEGORY_CACHE = "category";
    public static final String PRODUCT_CACHE = "product";

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
                        .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(60)));

        javax.cache.configuration.Configuration<Object, Object> ehcacheCacheConfiguration =
                Eh107Configuration.fromEhcacheCacheConfiguration(configuration);

        cacheManager.createCache(CATEGORY_CACHE, ehcacheCacheConfiguration);
        return new JCacheCacheManager(cacheManager);
    }

    @Bean
    public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration
                .defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext
                        .SerializationPair
                        .fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext
                        .SerializationPair
                        .fromSerializer(new GenericJackson2JsonRedisSerializer()));

        Map<String, RedisCacheConfiguration> cacheConfiguration = new HashMap<>();
        cacheConfiguration.put(PRODUCT_CACHE, redisCacheConfiguration.entryTtl(Duration.ofSeconds(60L)));

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration)
                .withInitialCacheConfigurations(cacheConfiguration)
                .build();
    }
}

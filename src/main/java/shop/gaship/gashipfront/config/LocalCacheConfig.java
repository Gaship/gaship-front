package shop.gaship.gashipfront.config;

import java.util.List;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    public CacheManager cacheManager() {
        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        simpleCacheManager.setCaches(List.of(new ConcurrentMapCache(CATEGORY_CACHE),
                new ConcurrentMapCache(PRODUCT_CACHE)));
        return simpleCacheManager;
    }
}

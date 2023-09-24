package learning.spring.config;

import learning.spring.binarytea.BinaryTeaProperties;
import org.redisson.api.RedissonClient;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

// 为了演示自动配置的加载，故意不放在learning.spring.binarytea包里
@Configuration
@EnableConfigurationProperties(BinaryTeaProperties.class)
@ConditionalOnProperty(name = "binarytea.ready", havingValue = "true")
public class ShopConfiguration {
    @Bean
    CacheManager cacheManager(RedissonClient redissonClient) {
        Map<String, CacheConfig> config = new HashMap<>();

        // create "testMap" spring cache with ttl = 24 minutes and maxIdleTime = 12 minutes
        config.put("menu", new CacheConfig(3600 * 1000, 3600 * 1000));
        return new RedissonSpringCacheManager(redissonClient, config);
    }
}

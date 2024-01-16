package me.dio.credit.application.system.configuration

import org.springframework.cache.annotation.CacheConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import java.time.Duration

@Configuration
@CacheConfig(cacheNames = ["customersCache"])
class CacheConfig {
    @Bean
    fun cacheManager(connectionFactory: RedisConnectionFactory): RedisCacheManager {
        val configuration = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(10))

        return RedisCacheManager.builder(connectionFactory)
            .cacheDefaults(configuration)
            .transactionAware()
            .build()
    }
}
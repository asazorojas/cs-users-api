package com.cornershopapp.usersapi.configuration.cache;

import com.cornershopapp.usersapi.domain.dtos.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

@Configuration
//@EnableCaching
public class CacheConfig {
    private final ObjectMapper objectMapper;

    public CacheConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        Jackson2JsonRedisSerializer<UserDTO> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(UserDTO.class);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        return org.springframework.data.redis.cache.RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(60))
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer));
    }

    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        return (builder) -> builder
                .withCacheConfiguration("usersCache",
                        org.springframework.data.redis.cache.RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10)))
                .withCacheConfiguration("userCache",
                        org.springframework.data.redis.cache.RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(5)));
    }
}

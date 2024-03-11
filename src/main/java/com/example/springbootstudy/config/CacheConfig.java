package com.example.springbootstudy.config;

import com.example.springbootstudy.pojo.Result;
import com.example.springbootstudy.pojo.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Configuration
@EnableCaching
public class CacheConfig {

    @Resource
    private RedisConnectionFactory connectionFactory;
    private static final long MIN_EXPIRATION_SECONDS = 300; // 最小过期时间，单位为秒
    private static final long MAX_EXPIRATION_SECONDS = 1800; // 最大过期时间，单位为秒
    @Bean
    public CacheManager cacheManager() {
        RedisCacheManager cacheManager = RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(createDefaultCacheConfiguration())
                .withInitialCacheConfigurations(createCacheConfigurations())
                .transactionAware()
                .build();
        return cacheManager;
    }

    private RedisCacheConfiguration createDefaultCacheConfiguration() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        serializer.setObjectMapper(objectMapper);

        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10))
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer));
    }

    private Map<String, RedisCacheConfiguration> createCacheConfigurations() {
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();

        cacheConfigurations.put("user", createCacheConfigurationForClass(User.class));
        cacheConfigurations.put("result", createCacheConfigurationForClass(Result.class));
        //cacheConfigurations.put("order", createCacheConfigurationForClass(Order.class));
        // 可以添加其他类的缓存配置

        return cacheConfigurations;
    }
    private <T> RedisCacheConfiguration createCacheConfigurationForClass(Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        Jackson2JsonRedisSerializer<T> serializer = new Jackson2JsonRedisSerializer<>(clazz);
        serializer.setObjectMapper(objectMapper);

        RedisSerializationContext.SerializationPair<T> serializationPair =
                RedisSerializationContext.SerializationPair.fromSerializer(serializer);

        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(getRandomExpiration()))
                .serializeValuesWith(serializationPair);
    }
    //获取随机缓存时间
    private long getRandomExpiration() {
        return MIN_EXPIRATION_SECONDS + new Random().nextInt((int) (MAX_EXPIRATION_SECONDS - MIN_EXPIRATION_SECONDS + 1));
    }

}
package cn.daycode.fatalism.depository.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class RedisConfig {

//    @Bean
//    public org.springframework.cache.Cache cache(StringRedisTemplate redisTemplate){
//        RedisCache redisCache = new RedisCache(redisTemplate);
//        return redisCache;
//    }
}

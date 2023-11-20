package cn.daycode.fatalism.sms.config;


import cn.daycode.fatalism.sms.common.cache.Cache;
import cn.daycode.fatalism.sms.common.cache.RedisCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;


@Configuration
public class RedisConfig {
	
	@Bean
	public Cache cache(StringRedisTemplate redisTemplate){
		return new RedisCache(redisTemplate);
	}
	

}

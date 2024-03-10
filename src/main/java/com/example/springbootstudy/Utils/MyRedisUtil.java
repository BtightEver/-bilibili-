package com.example.springbootstudy.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author luft-mensch
 */
@Component
public class MyRedisUtil {

    /**
     * 如果使用 @Autowired 注解完成自动装配 那么
     * RedisTemplate要么不指定泛型,要么泛型 为<Stirng,String> 或者<Object,Object>
     * 如果你使用其他类型的 比如RedisTemplate<String,Object>
     * 那么请使用 @Resource 注解
     * */
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 缓存value
     *
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean cacheValue(String key, Object value, long time) {
        try {
            ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
            valueOperations.set(key, value);
            if (time > 0) {
                // 如果有设置超时时间的话
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Throwable e) {
            logger.error("缓存[" + key + "]失败, value[" + value + "] " + e.getMessage());
        }
        return false;
    }

    /**
     * 缓存value，没有设置超时时间
     *
     * @param key
     * @param value
     * @return
     */
    public boolean cacheValue(String key, Object value) {
        return cacheValue(key, value, -1);
    }

    /**
     * 判断缓存是否存在
     *
     * @param key
     * @return
     */
    public boolean containsKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Throwable e) {
            logger.error("判断缓存是否存在时失败key[" + key + "]", "err[" + e.getMessage() + "]");
        }
        return false;
    }

    /**
     * 根据key，获取缓存
     *
     * @param key
     * @return
     */
    public Object getValue(String key) {
        try {
            ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
            return valueOperations.get(key);
        } catch (Throwable e) {
            logger.error("获取缓存时失败key[" + key + "]", "err[" + e.getMessage() + "]");
        }
        return null;
    }

    /**
     * 移除缓存
     *
     * @param key
     * @return
     */
    public boolean removeValue(String key) {
        try {
            redisTemplate.delete(key);
            return true;
        } catch (Throwable e) {
            logger.error("移除缓存时失败key[" + key + "]", "err[" + e.getMessage() + "]");
        }
        return false;
    }

    /**
     * 根据前缀移除所有以传入前缀开头的key-value
     *
     * @param pattern
     * @return
     */
    public boolean removeKeys(String pattern) {
        try {
            Set<String> keySet = redisTemplate.keys(pattern + "*");
            redisTemplate.delete(keySet);
            return true;
        } catch (Throwable e) {
            logger.error("移除key[" + pattern + "]前缀的缓存时失败", "err[" + e.getMessage() + "]");
        }
        return false;
    }

}
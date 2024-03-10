package com.example.springbootstudy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.io.Serializable;


public class RedisService {

    @Autowired
    RedisTemplate redisTemplate;

    //从Redis缓存中获取数据
    public Object getValue(final String key){

        Object result = null;

        try {
            ValueOperations<Serializable, Object> valueOps = redisTemplate.opsForValue();     //获取操作序列化对象Serializable的ValueOperations对象
            result = valueOps.get(key);     //根据key获取对象
        } catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    //向Redis中写入数据
    public boolean setValue(final String key, Object value){
        boolean result = false;

        try {
            ValueOperations<Serializable, Object> valueOps = redisTemplate.opsForValue();
            valueOps.set(key, value);
            result = true;
        } catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    //删除redis缓存中的key
    public void deleteValue(final String key){

        if(exists(key)){
            redisTemplate.delete(key);
        }
    }

    //判断redis缓存中是否有相应的key
    public boolean exists(final String key){
        return redisTemplate.hasKey(key);
    }
}

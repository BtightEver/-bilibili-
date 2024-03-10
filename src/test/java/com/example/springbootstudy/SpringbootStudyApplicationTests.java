package com.example.springbootstudy;

import com.example.springbootstudy.pojo.User;
import com.example.springbootstudy.service.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;
import java.io.Serializable;

@SpringBootTest
class SpringbootStudyApplicationTests {

    @Test
    void contextLoads() {
    }
}

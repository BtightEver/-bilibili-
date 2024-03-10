package com.example.springbootstudy.config;

import com.example.springbootstudy.Utils.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    //登录拦截
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jweInterceptor())   //配置jwt的拦截器规则
                .addPathPatterns("/**");    //拦截所有的请求路径
        super.addInterceptors(registry);
    }

    @Bean
    public JwtInterceptor jweInterceptor(){
        return new JwtInterceptor();
    }
}


package com.example.springbootstudy.Utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.springbootstudy.config.AuthAccess;
import com.example.springbootstudy.exception.ServiceException;
import com.example.springbootstudy.mapper.UserMapper;
import com.example.springbootstudy.pojo.User;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtInterceptor implements HandlerInterceptor {

    @Resource
    UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

            String token = request.getHeader("token");
            if (token == null) {
                token = request.getParameter("token");
            }

            // 如果不是映射到方法直接返回
            if(handler instanceof HandlerMethod){
                AuthAccess annotation = ((HandlerMethod) handler).getMethodAnnotation(AuthAccess.class);
                if(annotation != null){
                    return true;
                }
            }

            if(token == null) {
                throw new ServiceException("请登录");
            }

            String Uid;
            try{
                Uid = JWT.decode(token).getAudience().get(0);
            }catch (Exception e){
                throw new ServiceException("请登录");
            }

            User user = userMapper.getUserByUid(Uid);

            if(user == null){
                throw new ServiceException("请登录");
            }

            //通过用户密码加密之后生成一个验证器
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();

            try{
                jwtVerifier.verify(token);      //验证 token，这步最重要，在这里进行验证
            }catch (Exception e){
                throw new ServiceException("请登录");
            }

            return true;
        }
}

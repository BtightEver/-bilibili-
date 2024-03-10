package com.example.springbootstudy.Utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.springbootstudy.mapper.UserMapper;
import com.example.springbootstudy.pojo.User;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class JwtUtil {

    private static UserMapper staticUserMapper;

    @Resource
    UserMapper userMapper;

    @PostConstruct
    public void setUserService(){
        staticUserMapper = userMapper;
    }

    public static String createToken(String Uid, String sign){
        return JWT.create().withAudience(Uid)    //将 userId 保存到 token里面，作为载荷
                .withExpiresAt(Date.from(LocalDateTime.now().plusHours(2).atZone(ZoneId.systemDefault()).toInstant()))      //两小时后token到期
                .sign(Algorithm.HMAC256(sign));     //以 password 作为 token 的密钥
    }

    public static User getCurrentUser(){
        try{
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            if(token != null){
                String Uid = JWT.decode(token).getAudience().get(0);     //获取 token 的第一个数据，即userId
                return staticUserMapper.getUserByUid(Uid);
            }
        }catch (Exception e){
            return null;
        }

        return null;
    }


}

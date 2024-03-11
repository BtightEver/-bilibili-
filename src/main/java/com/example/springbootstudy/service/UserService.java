package com.example.springbootstudy.service;

import com.auth0.jwt.JWT;
import com.example.springbootstudy.Utils.*;
import com.example.springbootstudy.exception.ServiceException;
import com.example.springbootstudy.mapper.UserMapper;
import com.example.springbootstudy.pojo.Result;
import com.example.springbootstudy.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;


@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    @Resource
    MyRedisUtil myRedisUtil;

    public User Login(User user){

        User dbuser = userMapper.getUserByUid(user.getUid());

        if(dbuser == null){
            throw new ServiceException("用户不存在!");
        }
        user.setPassword(EncoderUtil.encode(user.getPassword()));
        if(!user.getPassword().equals(dbuser.getPassword())){
            throw new ServiceException("密码错误！");
        }

        String token = JwtUtil.createToken(String.valueOf(dbuser.getUid()), dbuser.getPassword());
        dbuser.setToken(token);
        return dbuser;
    }

    public Result Registery(@RequestBody User user) throws Exception {
        user.setCreateTime(new Date());
        user.setPassword(EncoderUtil.encode(user.getPassword()));
        System.out.println("用户信息："+user.toString());
        JedisConnectionPool jedisPool = JedisConnectionPool.getInstance("47.109.132.129", 6379, "wlmxhyqrfd1.");
        Jedis jedis = jedisPool.getConnection();
        user.setUid(LuaUtil.getNextUid(jedis));
        String originalAvatar = "http://localhost:8088/User/avatarDownload/originalAvatar.jpg";
        userMapper.addUser(user);
        return Result.ok();
    }

    public Result Update(@RequestBody User user){

        System.out.println("更新的用户信息："+user);
        String Uid = user.getUid();
        int stateCode = userMapper.updateUser(user);

        if(stateCode > 0){
            myRedisUtil.cacheValue(Uid, user, 3600 * 48);
            return Result.ok().data("user", user);
        }

        return Result.error();
    }

    public Result UpdatePassword(@RequestBody User user){
        User dbuser = userMapper.getUserByUid(user.getUid());
        if(dbuser == null){
            return Result.error().data("message","用户不存在");
        }
        user.setPassword(EncoderUtil.encode(user.getPassword()));
        if(!user.getPassword().equals(dbuser.getPassword())){
            return Result.error().data("message","密码错误");
        }
        user.setPassword(EncoderUtil.encode(user.getNewPassword()));
        userMapper.updatePassword(user);
        return Result.ok().data("message","更新密码成功");
    }
    @Cacheable("result")
    public Result getUserByUid(String uid){
        
        User dbuser = userMapper.getUserByUid(uid);     //先从 Redis 缓存中取

        if(dbuser != null){
            return Result.ok().data("user", dbuser);
        }

        return Result.error();
    }

    public List<User> getAllUsers(){
        return userMapper.getAllUsers();
    }

    //================================================用户头像上传下载================================================
    public Result avatarUpload(@PathVariable MultipartFile file, HttpServletRequest request) throws Exception{

        //从返回的 request 请求头中获取 token， 再从 token 中获得用户的 uid
        String Uid;
        String token = request.getHeader("token");
        try{
            Uid = JWT.decode(token).getAudience().get(0);
        }catch (Exception e){
            throw new ServiceException("请登录");
        }
        //从返回的 request 请求头中获取 token， 再从 token 中获得用户的 uid
        if(file == null){
            return Result.error();
        }

        String originalFilename = file.getOriginalFilename();
        String fileType = "";

        if (originalFilename != null) {
            int dotIndex = originalFilename.lastIndexOf(".");
            if (dotIndex > 0) {
                fileType = originalFilename.substring(dotIndex + 1);
            }
        }

        // 获取当前时间
        LocalDateTime currentTime = LocalDateTime.now();
        // 定义日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        // 将时间转换为字符串
        String currentTimeString = currentTime.format(formatter);
        System.out.println("======当前时间："+currentTimeString);

        String avatarName = Uid + "_" + currentTimeString + "." + fileType;
        String fileRealPath = "D:\\Practical_training\\VideoSite\\src\\assets\\images\\usersAvatars" + "\\" + avatarName;

        File saveFile = new File(fileRealPath);

        if(!saveFile.getParentFile().exists()){     //首先判断父级文件夹是否存在，如果不存在，则创建
            saveFile.getParentFile().mkdirs();
        }

        file.transferTo(saveFile);      //存储文件，存储到本地的磁盘路径

        String path = "http://localhost:8088/User/avatarDownload/" + avatarName;;
        User user = userMapper.getUserByUid(Uid);
        user.setAvatar(path);
        int stateCode = userMapper.updateUser(user);

        if(stateCode > 0){
            System.out.println("更新头像成功，文件存储路径是："+fileRealPath);
            return Result.ok().data("path", path);
        }

        System.out.println("更新头像失败！");
        return Result.error();
    }

    public void avatarDownload(String fileName, HttpServletResponse response) throws IOException {

        String projectPath = "D:\\Practical_training\\VideoSite\\src\\assets\\images\\usersAvatars";
        String filePath = projectPath + "\\" + fileName;
        System.out.println("download image：" + filePath);
        File readFile = new File(filePath);
        if(!FileUtil.exist(readFile)){
            return;
        }

        byte[] bytes = FileUtil.readFileBytes(filePath);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);      //数组是一个字节数组，存储文件的字节流
        outputStream.flush();
        outputStream.close();
    }
    //================================================用户头像上传下载================================================
}

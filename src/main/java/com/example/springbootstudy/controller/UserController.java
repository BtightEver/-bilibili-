package com.example.springbootstudy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootstudy.Utils.MyRedisUtil;
import com.example.springbootstudy.config.AuthAccess;
import com.example.springbootstudy.mapper.UserMapper;
import com.example.springbootstudy.pojo.Result;
import com.example.springbootstudy.pojo.User;
import com.example.springbootstudy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/User")
@ResponseBody
public class UserController {

    @Autowired  // 注入bean
    private UserMapper UserMapper;
    @Autowired
    private MyRedisUtil myRedisUtil;

    @Resource
    UserService userService;

    @AuthAccess
    @GetMapping("/getAllUsers")
    public Result gtAllUsers(){

        List<User> userList = userService.getAllUsers();
        System.out.println(userList);
        return Result.ok().data("userList", userList);
    }

    @AuthAccess
    @GetMapping("getUserByUid/{uid}")
    public Result getUserById(@PathVariable String uid){

        return userService.getUserByUid(uid);
    }

    @AuthAccess
    @PostMapping("/login")
    public Result login(@RequestBody User user)throws Exception{

        System.out.println(user.toString());
        if(user.getUid() == null || user.getPassword() == null){
            return Result.error();
        }

        user = userService.Login(user);
        return Result.ok().data("user", user);
    }

    @AuthAccess
    @PostMapping("/registery")
    public Result registery(@RequestBody User user) throws Exception {
        System.out.println("收到了注册请求");
       return userService.Registery(user);
    }

    @AuthAccess
    @RequestMapping("/update")
    public Result update(@RequestBody User user){
        return userService.Update(user);
    }

    @AuthAccess
    @PostMapping("/updatePassword")
    public Result updatePassword(@RequestBody User user) throws Exception {
        System.out.println("新的密码是"+user.getNewPassword());
        return userService.UpdatePassword(user);
    }

    @AuthAccess
    @RequestMapping("/avatarUpload")
    public Result avatarUpload(@PathVariable MultipartFile file, HttpServletRequest request) throws Exception {

        return userService.avatarUpload(file, request);
    }

    @AuthAccess
    @RequestMapping("/avatarDownload/{fileName}")       //图片下载后面要带上图片的文件名参数fileName
    public void avatarDownload(@PathVariable String fileName, HttpServletResponse response) throws IOException {

        userService.avatarDownload(fileName, response);
    }
    @AuthAccess
    @RequestMapping("/getUserByPage")   //分页查询
    public IPage getUserByPage(){

        Page<User> page = new Page<>(0, 3); //从第几个数据开始，数据的长度，如从0开始的后面3个数据
        IPage iPage = UserMapper.selectPage(page, null);
        return iPage;
    }

}

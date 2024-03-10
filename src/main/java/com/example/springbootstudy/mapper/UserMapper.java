package com.example.springbootstudy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootstudy.pojo.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Mapper // @Mapper 注解会将这个接口解释为mapper,UserMapper就可以实例化为对象。
@Component  //在IOC容器中注册bean，bean的id为该类的名称的小写-usermapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from users")   //简单的 sql 语句可以使用注解实现，复杂的 sql 语句需要写配置文件*.xml
    public List<User> getAllUsers();
    @Insert("insert into users (uid, user_name, password, create_time, avatar, token) values (#{uid}, #{userName}, #{password}, #{createTime}, #{avatar}, #{token})")     //sql语句中传参用#{}
    public int addUser(User user);

    @Update("update users set user_name = #{userName}, avatar = #{avatar} where uid = #{uid}")
    public int updateUser(User user);

    @Update("update users set password = #{password} where uid = #{uid}")
    public int updatePassword(User user);

    public int deleteUser(int id);

    public Result login(User user);
    public Result registery(User user);

    public IPage getUserByPage(Page page);

    @Select("select * from users where uid=#{uid}")
    public User getUserByUid(String uid);

    public Result avatarUpload(MultipartFile file, String Uid);
    public void avatarDownload(String fileName, HttpServletResponse response);
}

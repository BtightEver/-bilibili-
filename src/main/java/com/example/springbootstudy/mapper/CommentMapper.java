package com.example.springbootstudy.mapper;

import com.example.springbootstudy.pojo.Comment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Mapper
@Component
public interface CommentMapper {

    @Insert("insert into comments (cid, uid, commentator, video_id, likes, dislikes, create_time, content)" +
            "values (#{cid}, #{uid}, #{commentator}, #{videoId}, #{likes}, #{dislikes}, #{createTime}, #{content})")
    public int addComment(@RequestBody Comment comment);

    @Select("select * from comments where video_id = #{videoID}")
    public List<Comment> getCommentsByVideoId(String videoId);

    @Select("select * from comments where uid = #{Uid}")
    public List<Comment> getCommentsByUid(String Uid);

    @Update("update comments set likes = likes + 1 where cid = #{Cid}")
    public int LikeThisComment(String Cid);

    @Update("update comments set Dislikes = Dislikes + 1 where cid = #{Cid}")
    public int DislikeThisComment(String Cid);

    @Delete("delete from comments where cid = #{Cid}")
    public int DeleteComment(String Cid);
}

package com.example.springbootstudy.service;

import com.example.springbootstudy.mapper.CommentMapper;
import com.example.springbootstudy.pojo.Comment;
import com.example.springbootstudy.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    CommentMapper commentMapper;

    public Result addComment(@RequestBody Comment comment){

        Date currentDate = new Date();

        comment.setCreateTime(currentDate);
        System.out.println(comment);
        int stateCode = commentMapper.addComment(comment);

        if(stateCode > 0){
            return Result.ok().data("comment", comment);
        }

        return Result.error();
    }

    public List<Comment> getCommentsByVideoId(String VideoId){

        return commentMapper.getCommentsByVideoId(VideoId);
    }

    public List<Comment> getCommentsByUid(String VideoId){

        return commentMapper.getCommentsByVideoId(VideoId);
    }

    public int LikeThisComment(String Cid){

        return commentMapper.LikeThisComment(Cid);
    }

    public int DislikeThisComment(String Cid){

        return commentMapper.DislikeThisComment(Cid);
    }

    public int DeleteComment(String Cid){

        return commentMapper.DeleteComment(Cid);
    }
}

package com.example.springbootstudy.controller;

import com.example.springbootstudy.config.AuthAccess;
import com.example.springbootstudy.pojo.Comment;
import com.example.springbootstudy.pojo.Result;
import com.example.springbootstudy.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Comment")
@ResponseBody
public class CommentController {

    @Autowired
    CommentService commentService;

    @AuthAccess
    @RequestMapping("/addComment")
    public Result addComment(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }

    @AuthAccess
    @RequestMapping("/getCommentsByVideoId/{videoId}")
    public Result getCommentsByVideoId(@PathVariable String videoId){       //前端传过来的参数需要用 @PathVariable 注解

        System.out.println("获取视频 id 为 videoId："+videoId + " 的所有评论");
        List<Comment> commentList = commentService.getCommentsByVideoId(videoId);

        return Result.ok().data("commentList", commentList);
    }

    @AuthAccess
    @RequestMapping("/getCommentsByUid/{uid}")
    public Result getCommentsByUid(@PathVariable String uid){

        List<Comment> commentList = commentService.getCommentsByUid(uid);

        return Result.ok().data("commentList", commentList);
    }

    @AuthAccess
    @RequestMapping("/likeThisComment/{cid}")
    public Result LikeThisComment(@PathVariable String cid){

        int stateCode = commentService.LikeThisComment(cid);

        if(stateCode > 0){
            return Result.ok();
        }

        return Result.error();
    }

    @AuthAccess
    @RequestMapping("/dislikeThisComment/{cid}")
    public Result DislikeThisComment(@PathVariable String cid){

        int stateCode = commentService.DislikeThisComment(cid);

        if(stateCode > 0){
            return Result.ok();
        }

        return Result.error();
    }

    @AuthAccess
    @RequestMapping("/deleteComment/{cid}")
    public Result DeleteComment(@PathVariable String cid){

        System.out.println("cid："+cid);
        int stateCode = commentService.DeleteComment(cid);

        if(stateCode > 0){
            return Result.ok();
        }

        return Result.error();
    }
}

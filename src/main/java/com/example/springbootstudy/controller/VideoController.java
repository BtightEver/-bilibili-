package com.example.springbootstudy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootstudy.config.AuthAccess;
import com.example.springbootstudy.pojo.Video;
import com.example.springbootstudy.mapper.VideoMapperPlus;
import com.example.springbootstudy.service.VideoService;
import com.example.springbootstudy.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Transactional
@RestController
@RequestMapping("/videos")
public class VideoController {
    @Autowired
    private VideoService videoService;
    @Autowired
    private VideoMapperPlus videoMapperPlus;
   /* public Result getVideo() String fileName) {
        // 处理逻辑
        Video video=new Video();
        try{
        }catch (Exception e){
            return Result.error();
        }
        return Result.ok().data("video",video);
    }*/

    @PutMapping()
    @AuthAccess
    public Result modifyVideo(Video video,MultipartFile imageFile) {
        // 处理逻辑
        if(video.getUid()==null){
            return Result.error().data("message","uid为null");
        }
        if(video.getVideoId()==null){
            return Result.error().data("message","videoID为null");
        }
        try {
            System.out.println("访问了modifyVideo");
            System.out.println("uid"+video.getUid());
            videoService.modify(video,imageFile);
        } catch (Exception e) {
            System.err.println("Exception："+e);
            return Result.error();
        }
        return Result.ok();
    }

    @PostMapping()
    @AuthAccess
    public Result uploadVideo(Video video, MultipartFile videoFile,MultipartFile imageFile) {
        // 处理逻辑
        if(video.getUid()==null){
            return Result.error().data("message","uid为null");
        }
        if(videoFile==null&&video.getVideoId()==null){
            return Result.error().data("message","请确保videoID与videoFile至少有一个不为null");
        }
        try {
            System.out.println("访问了uploadVideo");
            if(video.getVideoId()==null){
                video.setVideoId(video.getUid()+"_"+(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()));
            }
            System.out.println("uid"+video.getUid());
            if(imageFile==null){
            }
            videoService.saveVideo(video,videoFile,imageFile);
        } catch (Exception e) {
            System.err.println("Exception："+e);
            return Result.error();
        }
        return Result.ok();
    }

    @GetMapping()
    @AuthAccess
    public ResponseEntity<Resource> serveVideo(@RequestParam("uid") String uid, @RequestParam("videoId") String videoId ) {

        String fileName=videoId+".mp4";
        System.out.println("访问了video资源，filename:"+fileName);
        try {
            Resource videoResource = new ClassPathResource("static/" + uid + "/videos/" + fileName);

            if (videoResource.exists() && videoResource.isReadable()) {
                MediaType mediaType = MediaTypeFactory.getMediaType(videoResource).orElse(MediaType.APPLICATION_OCTET_STREAM);
                videoMapperPlus.increaseViews(videoId);
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                        .contentType(mediaType)
                        .body(videoResource);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/list")
    @AuthAccess
    public Result query(@RequestParam("pageIndex") int pageIndex, @RequestParam("size") int size){
        try{
            Page<Video> page = new Page<>(pageIndex, size);  // 0表示页数，2表示每页多少个
            QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
            IPage ipage = videoMapperPlus.selectPage(page, queryWrapper);
            System.out.println(ipage);
            return Result.ok().data("videoList",ipage);
        }catch (Exception e){
            return Result.error().data("message","在query处遇到了错误");
        }
    }

    @GetMapping("/getVideosByInput/{input}")
    @AuthAccess
    public Result getVideosByVideoId(@PathVariable String input){         //根据 videoId 模糊查询视频

        System.out.println("========================input："+input);
        List<List<Video>> videoLists = videoService.getVideosByInput(input);
        return Result.ok().data("videoLists", videoLists);
    }
}
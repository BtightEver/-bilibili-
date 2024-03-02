package com.yongming.demo.controller;

import com.yongming.demo.entity.Video;
import com.yongming.demo.service.videoService;
import com.yongming.demo.utils.JwtUtils;
import com.yongming.demo.utils.Result;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jdk.nashorn.internal.ir.RuntimeNode;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Transactional
@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/videos")
public class VideoController {
    @Autowired
    private videoService videoService;
   /* public Result getVideo() String fileName) {
        // 处理逻辑
        Video video=new Video();
        try{
        }catch (Exception e){
            return Result.error();
        }
        return Result.ok().data("video",video);
    }*/
   @PutMapping("/{videoId}")
   public Result updateVideo(@PathVariable("videoId") String videoId, @RequestBody Video updatedVideo) {
       try {
           // 根据 videoId 和 updatedVideo 进行相应的更新逻辑

           // 示例：通过 videoId 获取原始视频对象，
           Video existingVideo = videoService.getVideoById(videoId);
           //更新其属性
           existingVideo.setVideoName(updatedVideo.getVideoName());
           existingVideo.setVideoDescription(updatedVideo.getVideoDescription());
           // ...


           // 保存更新后的视频对象
           videoService.updateVideo(existingVideo);

           return Result.ok();
       } catch (Exception e) {
           return Result.error();
       }
   }
    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping()
    public Result uploadVideo(Video video, MultipartFile file) {
        // 处理逻辑
        try {
            System.out.println("访问了uploadVideo");
            videoService.saveVideo(video,file);
        } catch (Exception e) {
            System.err.println("Exception："+e);
            return Result.error();
        }
        return Result.ok();
    }
    @GetMapping("")
    public ResponseEntity<Resource> serveVideo(@RequestParam("uid") String uid, @RequestParam("videoId") String videoId, HttpServletRequest request ) {

        String fileName=videoId+".mp4";
        System.out.println("访问了video资源，filename:"+fileName);
        try {
            Resource videoResource = new ClassPathResource("static/" + uid + "/videos/" + fileName);

            if (videoResource.exists() && videoResource.isReadable()) {
                MediaType mediaType = MediaTypeFactory.getMediaType(videoResource).orElse(MediaType.APPLICATION_OCTET_STREAM);

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
}
package com.yongming.demo.service;

import com.yongming.demo.entity.Video;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class videoService {
    //未实现
    public Video getVideoById(String videoId){
        Video video=new Video();
        return video;
    }
    //unwritten
    public boolean saveVideo(Video video, MultipartFile file){
        saveVideo2File(video.getAuthorId(),video.getVideoId(),file);
        return true;
    }
    //unwritten
    public boolean updateVideo(Video video){

        return true;
    }

    public boolean saveVideo2File(String uid, String filename, MultipartFile video) {
        String directoryPath = "D:\\大学作业\\青软\\web后端\\bilibili\\src\\main\\resources\\static/" + uid + "/videos/";
        File directory = new File(directoryPath);

        if (!directory.exists()) {
            directory.mkdirs(); // 创建目录
        }

        String filePath = directoryPath + filename+".mp4";
        File videoFile = new File(filePath);

        try {
            video.transferTo(videoFile); // 将 MultipartFile 对象保存为文件
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

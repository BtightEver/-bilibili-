package com.example.springbootstudy.service;

import com.example.springbootstudy.pojo.Video;
import com.example.springbootstudy.mapper.VideoMapperPlus;
import com.example.springbootstudy.Utils.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class VideoService {

    @Autowired
    private VideoMapperPlus videoMapperPlus;

    public List<List<Video>> getVideosByInput(String input){

        List<List<Video>> videoLists = new ArrayList<>();

        //因为可能会有许多视频是重复的，所以需要进行视频去重
        videoLists.add(videoMapperPlus.getVideosByVideoId(input));
        videoLists.add(videoMapperPlus.getVideosByVideoName(input));
        videoLists.add(videoMapperPlus.getVideosByCreatorName(input));

        return videoLists;
    }

    //unwritten
    public boolean saveVideo(Video video, MultipartFile videoFile,MultipartFile imageFile){
        saveVideo2File(video,videoFile,imageFile);
        saveVideo2MYSQL(video);
        return true;
    }
    public boolean saveVideo2MYSQL(Video video){
        String create_Time=(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        video.setUploadTime(new Date());
        int re=videoMapperPlus.insert(video);
        if(re>0) return true;
        return false;
    }
    //unwritten
    public boolean updateVideo(Video video){

        return true;
    }

    public boolean saveVideo2File(Video video, MultipartFile videoFile,MultipartFile imageFile) {
        //对videoFile的操作
        String videoPath = "D:\\大学作业\\青软\\web后端\\bilibili\\src\\main\\resources\\static/" + video.getUid() + "/videos/";
        String videoFilePath = videoPath + video.getVideoId()+".mp4";
        File videoToFile = new File(videoFilePath);
        if(videoFile!=null){
            File videoDirectory = new File(videoPath);
            if (!videoDirectory.exists()) {
                videoDirectory.mkdirs(); // 创建目录
            }
            int videoSeconds=ImageUtil.getVideoDurationInSeconds(videoFilePath);
            video.setVideoSeconds(videoSeconds);
            video.setSavePath(videoFilePath);
        }
        String imagePath = "D:\\大学作业\\青软\\web后端\\bilibili\\src\\main\\resources\\static/" + video.getUid() + "/images/";
        File imageDirectory =new File(imagePath);
        if (!imageDirectory.exists()) {
            imageDirectory.mkdirs(); // 创建目录
        }
        String imageFilePath = imagePath + video.getVideoId()+".jpg";
        video.setImagePath(imageFilePath);
        File imageToFile = new File(imageFilePath);
        try {
            if(videoFile!=null){
                videoFile.transferTo(videoToFile); // 将 MultipartFile 对象保存为文件
            }
            if (imageFile==null){
                ImageUtil.saveRandomFrameFromVideo(videoFilePath,imageFilePath);
            }
            else {
                imageFile.transferTo(imageToFile); // 将 MultipartFile 对象保存为文件
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    //修改视频
    public boolean modify(Video video,MultipartFile imageFile){
        videoMapperPlus.updateById(video);
        if(imageFile!=null){
            String imagePath = "D:\\大学作业\\青软\\web后端\\bilibili\\src\\main\\resources\\static\\" + video.getUid() + "\\images\\";
            File imageDirectory =new File(imagePath);
            if (!imageDirectory.exists()) {
                imageDirectory.mkdirs(); // 创建目录
            }
            String imageFilePath = imagePath + video.getVideoId()+".jpg";
            video.setImagePath(imageFilePath);
            File imageToFile = new File(imageFilePath);
            try {
                    imageFile.transferTo(imageToFile); // 将 MultipartFile 对象保存为文件
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
}

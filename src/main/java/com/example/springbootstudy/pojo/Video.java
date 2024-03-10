package com.example.springbootstudy.pojo;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("videos")
public class Video {
    int id;
    //视频id，以uid+time制作存入数据库
    String videoId;
    //视频名称
    String videoName;
    //上传者id
    String uid;
    //视频简介
    String videoDescription;
    //收藏量
    int stars;
    //播放量
    int views;
    //保存路径
    String savePath;
    //封面图片保存路径
    String imagePath;
    //视频时长（秒）
    int videoSeconds;

    public Video(int id, String videoId, String videoName, String uid, String videoDescription, int stars, int views, String savePath, String imagePath, int videoSeconds, Date uploadTime, int likes) {
        this.id = id;
        this.videoId = videoId;
        this.videoName = videoName;
        this.uid = uid;
        this.videoDescription = videoDescription;
        this.stars = stars;
        this.views = views;
        this.savePath = savePath;
        this.imagePath = imagePath;
        this.videoSeconds = videoSeconds;
        this.uploadTime = uploadTime;
        this.likes = likes;
    }

    public int getVideoSeconds() {
        return videoSeconds;
    }

    public void setVideoSeconds(int videoSeconds) {
        this.videoSeconds = videoSeconds;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Video(int id, String videoId, String videoName, String uid, String videoDescription, int stars, int views, String savePath, Date uploadTime, int likes) {
        this.id = id;
        this.videoId = videoId;
        this.videoName = videoName;
        this.uid = uid;
        this.videoDescription = videoDescription;
        this.stars = stars;
        this.views = views;
        this.savePath = savePath;
        this.uploadTime = uploadTime;
        this.likes = likes;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public Video(){

    }
    public Video(int id, String videoId, String videoName, String uid, String videoDescription, Date uploadTime, int likes, int stars, int views) {
        this.id = id;
        this.videoId = videoId;
        this.videoName = videoName;
        this.uid = uid;
        this.videoDescription = videoDescription;
        this.uploadTime = uploadTime;
        this.likes = likes;
        this.stars = stars;
        this.views = views;
    }

    //视频上传时间
    Date uploadTime;
    //点赞数
    int likes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getVideoDescription() {
        return videoDescription;
    }

    public void setVideoDescription(String videoDescription) {
        this.videoDescription = videoDescription;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

}

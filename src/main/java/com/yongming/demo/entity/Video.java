package com.yongming.demo.entity;
import org.springframework.core.io.Resource;

import java.util.Date;


public class Video {
    int id;
    //视频id，以id+videoName制作存入数据库
    String videoId;
    //视频名称,在文件系统中考虑到防止同一用户上传两个相同视频名称的视频
    //所以文件系统中存储的视频名称应该是id+videoName(同videoId）
    String videoName;
    //上传者id
    String authorId;
    //视频简介
    String videoDescription;
    //视频上传时间
    Date uploadTime;
    //点赞数
    int likes;
    //收藏量
    int stars;
    //播放量
    int views;

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

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
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

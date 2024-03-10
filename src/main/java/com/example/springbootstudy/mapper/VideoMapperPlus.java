package com.example.springbootstudy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springbootstudy.pojo.Video;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface VideoMapperPlus extends BaseMapper<Video> {

    @Update("UPDATE videos SET views = views + 1 WHERE video_id = #{videoId}")
    void increaseViews(@Param("videoId") String videoId);

    @Select("select * from videos where video_id like '%' #{videoId} '%'")
    List<Video> getVideosByVideoId(String videoId);

    @Select("select * from videos where video_name like '%' #{videoName} '%'")
    List<Video> getVideosByVideoName(String videoName);

    @Select("select * from videos where creator_name like '%' #{creatorName} '%'")
    List<Video> getVideosByCreatorName(String creatorName);
}

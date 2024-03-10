package com.example.springbootstudy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Comment {

    private int id;
    private String cid;
    private String uid;
    private String commentator;
    private String videoId;
    private int likes;
    private int dislikes;
    private Date createTime;
    private String content;
}

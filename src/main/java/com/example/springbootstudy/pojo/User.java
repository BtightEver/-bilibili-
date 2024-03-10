package com.example.springbootstudy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class User implements Serializable {

    private int id;
    private String uid;
    private String userName;
    private String password;
    private Date createTime;
    private String avatar;
    private String token;
}

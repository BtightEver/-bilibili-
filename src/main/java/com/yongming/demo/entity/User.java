package com.yongming.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    @TableId(type = IdType.AUTO)
    private int id;
    private String uid;
    private String account;
    private String password;
    private String userName;
    private Date createTime;
    public int getId() {
        return id;
    }

    public User(int id, String uid, String account, String password, String userName, Date createTime) {
        this.id = id;
        this.uid = uid;
        this.account = account;
        this.password = password;
        this.userName = userName;
        this.createTime = createTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
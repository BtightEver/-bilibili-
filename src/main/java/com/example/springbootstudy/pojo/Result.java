package com.example.springbootstudy.pojo;

import com.example.springbootstudy.Utils.ResultCode;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class Result {
    private boolean success;
    private Integer code;
    private String message;
    private Map<String, Object> data=new HashMap<>();

    private Boolean getSuccess(){return success;}

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    private Result() {

    }
    public static Result ok(){
        Result r=new Result();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("成功！");
        return  r;
    }
    public static Result error(){
        Result r=new Result();
        r.setSuccess(true);
        r.setCode(ResultCode.ERROR);
        r.setMessage("失败！");
        return  r;
    }
    public Result data(String key, Object value){
        this.data.put(key,value);
        return this;
    }
    public Result data(Map<String,Object> map){
        this.setData(map);
        return this;
    }
    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}

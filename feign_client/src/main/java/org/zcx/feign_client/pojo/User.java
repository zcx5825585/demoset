package org.zcx.feign_client.pojo;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String name;
    private String username;
    private String password;
    private String token;
    private Boolean enable = true;

    private Map<String, BookInfo> bookInfoMap = new HashMap<>();

    private String bookResult;


    public String toJson() {
        return JSON.toJSONString(this);
    }

    public static User fromJson(String json) {
        return JSON.parseObject(json, User.class);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Map<String, BookInfo> getBookInfoMap() {
        return bookInfoMap;
    }

    public void setBookInfoMap(Map<String, BookInfo> bookInfoMap) {
        this.bookInfoMap = bookInfoMap;
    }

    public String getBookResult() {
        return bookResult;
    }

    public void setBookResult(String bookResult) {
        this.bookResult = bookResult;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}

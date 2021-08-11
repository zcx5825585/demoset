package org.zcx.feign_client.pojo;

import java.util.Map;

public class LibResult {
    private String status;
    private Map<String, Object> data;
    private String message;
    private String code;

    public static String success = "success";
    public static String fail = "fail";
    public static String loginFailMessage = "登录失败: 用户名或密码不正确";

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

package org.zcx.feign_client.service;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.zcx.feign_client.DAO.UserDAO;
import org.zcx.feign_client.feign.LibApi;
import org.zcx.feign_client.pojo.BookInfo;
import org.zcx.feign_client.pojo.LibResult;
import org.zcx.feign_client.pojo.User;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@EnableScheduling
public class UserService {
    @Autowired
    private LibApi libApi;
    @Autowired
    private UserDAO userDAO;

    private static final Map<String, User> userMap = new HashMap<>();
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    //启东时从文件加载信息
    @PostConstruct
    public void init() {
        //从文件读取
        List<User> userList = userDAO.getSavedUserList();
        for (User user : userList) {
            userMap.put(user.getName(), user);
        }
    }

    //为所有用户预约
    //每天5点零30秒执行
//    @Scheduled(cron = "30 0 5 * * ?")
    public void freeBookAll() {
        for (User user : userMap.values()) {
            if (user.getEnable()) {
                freeBook(user);
            }
        }
    }

    //获取用户列表
    public Collection<User> getUserList() {
        return userMap.values();
    }

    //获取用户详情
    public User getUser(String name) {
        return userMap.get(name);
    }

    //保存到文件
    public String saveToFile() {
        userDAO.saveUser(new ArrayList<>(userMap.values()));
        return "保存成功";
    }

    //添加用户
    public String createUser(String name, String username, String password) {
        User user = new User();
        user.setName(name);
        user.setUsername(username);
        user.setPassword(password);
        userMap.put(name, user);
        return "创建成功";
    }

    //删除用户
    public String deleteUser(String name) {
        userMap.remove(name);
        return "删除成功";
    }
    //用户开启预约
    public String enableUser(String name) {
        User user = userMap.get(name);
        user.setEnable(true);
        userMap.put(name,user);
        return "开启预约成功";
    }

    //用户关闭预约
    public String disableUser(String name) {
        User user = userMap.get(name);
        user.setEnable(false);
        userMap.put(name,user);
        return "关闭预约成功";
    }

    //为指定用户添加预约信息
    public String addBook(String name, BookInfo bookInfo) {
        User user = userMap.get(name);
        if (user == null) {
            return "用户不存在";
        }
        String key = UUID.randomUUID().toString();
        user.getBookInfoMap().put(key, bookInfo);
        return "添加成功";
    }

    //为指定用户删除预约信息
    public String removeBook(String name, String key) {
        User user = userMap.get(name);
        if (user == null) {
            return "用户不存在";
        }
        user.getBookInfoMap().remove(key);
        return "删除成功";
    }

    //签到
    public String checkIn(String name) {
        User user = userMap.get(name);
        if (user == null) {
            return "签到失败";
        }
        //失败重试两次
        for (int i = 0; i < 3; i++) {
            LibResult result = libApi.checkIn(user.getToken());
            if (LibResult.success.equals(result.getStatus())) {
                return "签到成功";
            } else if (LibResult.loginFailMessage.equals(result.getMessage())) {
                auth(user);
            }
        }
        return "签到失败";
    }

    //================================================================================
    //预约
    private String freeBook(User user) {
        List<BookInfo> bookInfoList = new ArrayList<>(user.getBookInfoMap().values());
        for (int i = 0; i < bookInfoList.size(); i++) {
            BookInfo bookInfo = bookInfoList.get(i);
            //生成复制
            BookInfo query = bookInfo.getQuery();
            if (query.getNextDay()) {
                //预约第二天
                query.setDateString(sdf.format(new Date(new Date().getTime() + 1000 * 60 * 60 * 24)));
            } else {
                //预约当天
                query.setDateString(sdf.format(new Date()));
            }
            //设置座位
            if (BookInfo.ANY_SEAT.equals(bookInfo.getSeat())) {
                query.setSeat(getAnySeat());
            } else {
                query.setSeat(bookInfo.getSeat());
            }

            //获取开始时间
            String startTime = getStartTime(query, user);
            if (startTime == null) {
                continue;
            }

            //获取结束时间
            String endTime = getEndTime(query, user);
            if (endTime == null) {
                continue;
            }

            LibResult result = libApi.freeBook(user.getToken(), query.getStartTime(), query.getEndTime(), query.getSeat(), query.getDateString());
            if (LibResult.fail.equals(result.getStatus())) {
                //预约失败
                if (LibResult.loginFailMessage.equals(result.getMessage())) {
                    //token错误
                    auth(user);
                    //重复上一个
                    i--;
                } else {
                    continue;
                }
            } else if (LibResult.success.equals(result.getStatus())) {
                //预约成功
                String bookResult = JSON.toJSONString(result.getData());
                user.setBookResult(bookResult);
                return bookResult;
            }
        }
        return "预约失败";
    }

    //获取任意可选座位
    private String getAnySeat() {
        return "20178";
    }

    //获取座位的最早可选时间,不超过设置的时间
    private String getStartTime(BookInfo bookInfo, User user) {
        //失败重试两次
        for (int i = 0; i < 3; i++) {
            LibResult result = libApi.getStartTime(bookInfo.getSeat(), bookInfo.getDateString(), user.getToken());
            if (LibResult.success.equals(result.getStatus())) {
                List<Map<String, String>> timeMap = (List<Map<String, String>>) result.getData().get("startTimes");
                Long startTime = timeMap.stream()
                        .mapToLong(map -> Long.parseLong(map.get("id")))
                        .filter(value -> value >= Long.parseLong(bookInfo.getStartTime()))
                        .min().orElse(-1L);
                if (startTime > 0) {
                    return String.valueOf(startTime);
                } else {
                    return null;
                }
            } else if (LibResult.loginFailMessage.equals(result.getMessage())) {
                auth(user);
            }
        }
        return null;
    }

    //获取座位的最晚可选时间,不晚于设置的时间
    private String getEndTime(BookInfo bookInfo, User user) {
        //失败重试两次
        for (int i = 0; i < 3; i++) {
            LibResult result = libApi.getEndTime(bookInfo.getSeat(), bookInfo.getDateString(), bookInfo.getStartTime(), user.getToken());
            if (LibResult.success.equals(result.getStatus())) {
                List<Map<String, String>> timeMap = (List<Map<String, String>>) result.getData().get("endTimes");
                Long startTime = timeMap.stream()
                        .mapToLong(map -> Long.parseLong(map.get("id")))
                        .filter(value -> value <= Long.parseLong(bookInfo.getEndTime()))
                        .max().orElse(-1L);
                if (startTime > 0) {
                    return String.valueOf(startTime);
                } else {
                    return null;
                }
            } else if (LibResult.loginFailMessage.equals(result.getMessage())) {
                auth(user);
            }
        }
        return null;
    }

    //更新token
    private String auth(User user) {
        LibResult loginResult = libApi.auth(user.getUsername(), user.getPassword());
        String token = (String) loginResult.getData().get("token");
        user.setToken(token);
        return token;
    }
}

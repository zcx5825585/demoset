package org.zcx.feign_client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.zcx.feign_client.pojo.BookInfo;
import org.zcx.feign_client.pojo.User;
import org.zcx.feign_client.service.UserService;

import java.util.Collection;

@RestController
public class BookController {
    @Autowired
    private UserService userService;

    //保存信息到文件
    @CrossOrigin
    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView("/index");
    }
    //保存信息到文件
    @CrossOrigin
    @GetMapping("saveData")
    public String saveData() {
        return userService.saveToFile();
    }

    //获取用户列表
    @CrossOrigin
    @GetMapping("list")
    public Collection<User> userList() {
        return userService.getUserList();
    }

    //创建用户
    @CrossOrigin
    @PostMapping
    public String login(@RequestBody User user) {
        return userService.createUser(user.getName(), user.getUsername(), user.getPassword());
    }
    //删除用户
    @CrossOrigin
    @DeleteMapping("/{name}")
    public String login(@PathVariable("name") String name) {
        return userService.deleteUser(name);
    }

    //用户开启预约
    @CrossOrigin
    @PutMapping("/{name}/enable")
    public String enableUser(@PathVariable("name") String name) {
        return userService.enableUser(name);
    }

    //用户关闭预约
    @CrossOrigin
    @PutMapping("/{name}/disable")
    public String disableUser(@PathVariable("name") String name) {
        return userService.disableUser(name);
    }

    //查看用户预约
    @CrossOrigin
    @GetMapping("/{name}/bookPlan")
    public Collection<BookInfo> getBookPlan(@PathVariable("name") String name) {
        User user = userService.getUser(name);
        return user.getBookInfoMap().values();
    }

    //为用户添加预约
    @CrossOrigin
    @PostMapping("/{name}/bookPlan")
    public String getBookPlan(@PathVariable("name") String name, @RequestBody BookInfo bookInfo) {
        return userService.addBook(name, bookInfo);
    }

    //删除用户预约
    @CrossOrigin
    @DeleteMapping("/{name}/bookPlan/{key}")
    public String removeBookPlan(@PathVariable("name") String name, @PathVariable("key") String key) {
        return userService.removeBook(name, key);
    }

    //用户签到
    @CrossOrigin
    @PutMapping("/{name}/checkIn")
    public String checkIn(@PathVariable("name") String name) {
        return userService.checkIn(name);
    }
}

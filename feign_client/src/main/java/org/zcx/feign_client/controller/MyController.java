package org.zcx.feign_client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zcx.feign_client.feign.LibApi;
import org.zcx.feign_client.pojo.LibResult;

@RestController
@RequestMapping("test")
public class MyController {
    @Autowired
    private LibApi libApi;

    @GetMapping("login")
    public LibResult login(@RequestParam String username, @RequestParam String password) {
        return libApi.auth(username, password);
    }

    @PostMapping("/freeBook")
    public LibResult freeBook(String startTime, String endTime, String seat, String date) {
        String token = "TR3ZVN8LCL08112202";
        return libApi.freeBook(token, startTime, endTime, seat, date);
    }

    @GetMapping(value = "/checkIn")
    public LibResult checkIn() {
        String token = "TR3ZVN8LCL08112202";
        return libApi.checkIn(token);
    }

    @GetMapping(value = "/history")
    public LibResult history() {
        String token = "TR3ZVN8LCL08112202";
        return libApi.history(1, 10, token);
    }
}

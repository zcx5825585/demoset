package zcx.com.example.same_url.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping
    public String saveDemoTest() {

        return String.valueOf(new Date());
    }

}

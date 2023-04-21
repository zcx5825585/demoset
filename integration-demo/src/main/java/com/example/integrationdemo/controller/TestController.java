package com.example.integrationdemo.controller;

import com.example.integrationdemo.MyMessageProducerSupport;
import com.example.integrationdemo.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/test")
public class TestController {
    @Resource
    private UserService userService;

    @Autowired
    @Qualifier("queueChannelMessageTemplate")
    MessagingTemplate queueMessageTemplate;

    @Resource
    MyMessageProducerSupport producerSupport;

    @GetMapping("/test")
    public String test(String msg){
        return msg;
    }
    @GetMapping("/send")
    public void send(String msg){
        userService.defaultPayload(msg,"zcx");
    }
    @GetMapping("/send2")
    public void send2(String msg){
        queueMessageTemplate.send(new GenericMessage<String>(msg));
    }
    @GetMapping("/send3")
    public void send3(String msg){
        producerSupport.receive(msg);
    }
}

package com.example.integrationdemo;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

/**
 * user service
 *
 * @author linux_china
 */
@Service
@MessagingGateway(defaultRequestChannel = "queueChannel")
public interface UserService {

    public void defaultPayload(String payload, @Header("test") String heard);
}

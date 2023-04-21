package com.example.integrationdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@DependsOn("queueChannel")
public class MyMessageProducerSupport extends MessageProducerSupport {

    @Autowired
    @Qualifier("queueChannel")
    MessageChannel queueChannel;

    @Override
    protected void onInit() {
        super.onInit();
        super.setOutputChannel(queueChannel);
    }

    @Override
    protected void doStart() {
        super.doStart();
        new Thread(()->{
            Map<String, Object> headers = new HashMap<>();
            headers.put("testHeader", "heartbeat");
            while (true) {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.sendMessage(new GenericMessage<String>("heartbeat", headers));
            }
        }).start();

    }

    public void receive(String msg) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("testHeader", "myMessageProducerSupport");
        this.sendMessage(new GenericMessage<String>(msg, headers));
    }

}

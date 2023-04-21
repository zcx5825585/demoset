package com.example.integrationdemo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.endpoint.ReactiveMessageSourceProducer;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.support.GenericMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * integration configuration
 *
 * @author linux_china
 */
@Configuration
public class IntegrationConfiguration {

    //创建通道
    @Bean("queueChannel")
    public MessageChannel queueChannel() {
        return new DirectChannel();
    }

    //设置消费者
    @Bean
    @ServiceActivator(inputChannel = "queueChannel")
    public MessageHandler queueMessageHandler() {
        return message -> {
            System.out.println("Handle Headers:" + message.getHeaders());
            System.out.println("Handle Payload:" + message.getPayload());
        };
    }

    //设置生产者
    @Bean
    public MessageProducer inbound() {
        MessageProducer messageProducer = new MessageProducerSupport() {
            public void receive() {
                //实现接受消息
                //发送到OutputChannel
                this.sendMessage(new GenericMessage<String>("receive"));
            }
        };
        // 设置订阅通道
        messageProducer.setOutputChannel(queueChannel());
        return messageProducer;
    }

    @Bean
    MessagingTemplate queueChannelMessageTemplate() {
        return new MessagingTemplate(queueChannel());
    }
}

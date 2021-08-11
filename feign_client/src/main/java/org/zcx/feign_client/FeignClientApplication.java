package org.zcx.feign_client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.zcx.feign_client.swing.SwingWin;

@EnableFeignClients
@SpringBootApplication
public class FeignClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(FeignClientApplication.class, args);
//        SpringApplicationBuilder builder = new SpringApplicationBuilder(FeignClientApplication.class);
//        builder.web(WebApplicationType.NONE);
//        ApplicationContext applicationContext = builder.headless(false).run(args);
//        SwingWin swing = applicationContext.getBean(SwingWin.class);
//        swing.create();
    }
}

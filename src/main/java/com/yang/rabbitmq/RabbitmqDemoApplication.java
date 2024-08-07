package com.yang.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * demo
 *
 * @author ssc
 */
@SpringBootApplication
public class RabbitmqDemoApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(RabbitmqDemoApplication.class);
        application.run(args);
        System.out.println("(♥◠‿◠)ﾉﾞ  rabbitmq  demo 启动成功   ლ(´ڡ`ლ)ﾞ  ");
    }
}

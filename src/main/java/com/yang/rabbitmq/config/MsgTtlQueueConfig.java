package com.yang.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息过期时间配置
 */
@Configuration
public class MsgTtlQueueConfig {
    private static final String Y_DEAD_LETTER_EXCHANGE = "Y";
    private static final String QUEUE_C = "QC";

    // 声明队列C 死信交换机
    @Bean("queueC")
    public Queue queueB(){
        Map<String, Object> args = new HashMap<>(3);
        // 声明当前队列绑定的死信交换机
        args.put("x-dead-letter-exchange",Y_DEAD_LETTER_EXCHANGE);
        // 声明当前队列的死信路由
        args.put("x-dead-letter-routing-key","YD");
        // 没有声明TTL属性
        return QueueBuilder.durable(QUEUE_C).withArguments(args).build();
    }

    // 声明队列B 绑定 X 交换机
    @Bean
    public Binding queueBindingX(@Qualifier("queueC") Queue queueC,
                                 @Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(queueC).to(xExchange).with("XC");
    }
}


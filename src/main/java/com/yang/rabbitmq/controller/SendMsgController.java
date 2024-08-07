package com.yang.rabbitmq.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author yangliangchuang
 * @date 2024/8/7 10:11
 */
@Slf4j
@RestController
@RequestMapping("/ttl/")
public class SendMsgController {

    public static final String DELAYED_EXCHANGE_NAME = "delayed.exchange";
    public static final String DELAYED_ROUTING_KEY = "delayed.routingkey";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMsg/{message}")
    public void receiveMessage(@PathVariable String message) {

        rabbitTemplate.convertAndSend("X", "XA", message);
        rabbitTemplate.convertAndSend("X", "XB", message);
        log.info("当前时间：{}, 发送一条消息给两个TTL队列:{}", new Date(), message);

    }

    @GetMapping("sendExpirationMsg/{message}/{ttlTime}")
    public void sendMsg(@PathVariable String message, @PathVariable String ttlTime) {
        rabbitTemplate.convertAndSend("X", "XC", message, correlationData -> {
            correlationData.getMessageProperties().setExpiration(ttlTime);
            return correlationData;
        });
        log.info("当前时间：{}, 发送一条时长{}毫秒TTL消息给队列C:{}", new Date(), ttlTime, message);
    }

    @GetMapping("sendDelayMsg/{message}/{delayTime}")
    public void sendMsg(@PathVariable String message, @PathVariable Integer delayTime) {
        rabbitTemplate.convertAndSend(DELAYED_EXCHANGE_NAME, DELAYED_ROUTING_KEY, message, correlationData -> {
            correlationData.getMessageProperties().setDelay(delayTime);
            return correlationData;
        });
        log.info("当前时间：{}，发送一条延迟 {} 毫秒的信息给队列 delayed.queue：{}", new Date(), delayTime, message);
    }

}

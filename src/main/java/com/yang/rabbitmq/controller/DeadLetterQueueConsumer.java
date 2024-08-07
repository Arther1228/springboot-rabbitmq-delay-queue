package com.yang.rabbitmq.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class DeadLetterQueueConsumer {

    public static final String DEAD_LETTER_QUEUE = "QD";

    @RabbitListener(queues = DEAD_LETTER_QUEUE)
    public void receiveDelayedQueue(Message message) {
        String msg = new String(message.getBody());
        MessageProperties messageProperties = message.getMessageProperties();
        List<Map<String, ?>> xDeathHeader = messageProperties.getXDeathHeader();
        log.info("当前时间：{}，收到延时队列的信息：{}，来自队列：{}", new Date(), msg, xDeathHeader);
    }
}

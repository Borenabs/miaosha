package com.lan.miaosha.rabbitmq;

import com.lan.miaosha.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQSender {

    @Autowired
    AmqpTemplate amqpTemplate;

    private static Logger logger = LoggerFactory.getLogger(MQSender.class);

    /*public void send(Object message){
        String msg = RedisService.beanToString(message);
        amqpTemplate.convertAndSend(MQConfig.QUEUE , msg);
        logger.info("send msg" + msg);
    }

    public void sendTopic_1(Object message){
        String msg = RedisService.beanToString(message);
        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE , MQConfig.ROUTING_KEY_1 , message);
    }

    public void sendTopic_2(Object message){
        String msg = RedisService.beanToString(message);
        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE , MQConfig.ROUTING_KEY_2 , message);
    }*/


    public void sendMiaoshaMessage(MiaoshaMessage miaoshaMessage) {
        String message = RedisService.beanToString(miaoshaMessage);
        amqpTemplate.convertAndSend(MQConfig.MIAOSHA_QUEUE , message);
        logger.info("send miaosha message!");
    }
}

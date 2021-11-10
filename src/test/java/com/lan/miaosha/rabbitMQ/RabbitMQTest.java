package com.lan.miaosha.rabbitMQ;

import com.lan.miaosha.rabbitmq.MQSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMQTest {

    @Autowired
    MQSender sender;

    @Test
    public void senderTest(){
        sender.send("hello+++++");
    }

    @Test
    public void sendTopic(){

    }
}

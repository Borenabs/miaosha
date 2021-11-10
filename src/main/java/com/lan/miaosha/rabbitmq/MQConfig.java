package com.lan.miaosha.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MQConfig {

    public static final String QUEUE = "queue";

    public static final String TOPIC_QUEUE_1 = "TOPIC_QUEUE_1";
    public static final String TOPIC_QUEUE_2 = "TOPIC_QUEUE_2";
    public static final String TOPIC_EXCHANGE = "TOPIC_EXCHANGE";
    public static final String ROUTING_KEY_1 = "TOPIC_KEY_1";
    public static final String ROUTING_KEY_2 = "TOPIC_KEY_2";

    //秒杀Queue
    public static final String MIAOSHA_QUEUE = "miaoshaQueue";

    @Bean
    public Queue queue(){
        return new Queue(QUEUE , true);
    }

    @Bean
    public Queue topicQueue_1(){
        return new Queue(TOPIC_QUEUE_1 , true);
    }

    @Bean
    public Queue topicQueue_2(){
        return new Queue(TOPIC_QUEUE_2 , true);
    }

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    public Binding binding(){
        return BindingBuilder.bind(topicQueue_1()).to(topicExchange()).with(ROUTING_KEY_1);
    }

    @Bean
    public Binding binding_2(){
        return BindingBuilder.bind(topicQueue_2()).to(topicExchange()).with(ROUTING_KEY_2);
    }



}

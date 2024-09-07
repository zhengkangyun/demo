package com.learn.demo.basic;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

import static com.learn.demo.basic.Constants.QUEUE_NAME;

public class FirstConsumer {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = MQConnectionFactory.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,true,false,false,new HashMap<>());
        channel.basicQos(2);
        /**
         * 推送模式  绑定的Queue一旦有了消息  就自动处理
         */
        //回调函数
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,AMQP.BasicProperties properties,byte[] body){
                System.out.println("---------------------");
                String routingKey = envelope.getRoutingKey();
                System.out.println(routingKey);
                String contentType = properties.getContentType();
                long deliveryTag = envelope.getDeliveryTag();
                System.out.println(contentType);
                System.out.println(deliveryTag);
                System.out.println("content:"+new String(body));
            }
        };
        channel.basicConsume(QUEUE_NAME,true,consumer);


        /**
         * 拉取模式  一次性拉取消息进行处理   使用较少
         */
//        GetResponse getResponse = channel.basicGet(QUEUE_NAME,false);
//        byte[] bytes = getResponse.getBody();
//        Envelope envelope = getResponse.getEnvelope();
//        int messageCount = getResponse.getMessageCount();
//        AMQP.BasicProperties properties = getResponse.getProps();
//        System.out.println("---------------------");
//        String routingKey = envelope.getRoutingKey();
//        System.out.println(routingKey);
//        String contentType = properties.getContentType();
//        long deliveryTag = envelope.getDeliveryTag();
//        System.out.println(contentType);
//        System.out.println(deliveryTag);
//        System.out.println("content:"+new String(bytes));
//        channel.basicAck(deliveryTag,false);

        // 释放资源
//        channel.close();
//        connection.close();
    }
}

package com.learn.demo.basic;


import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import static com.learn.demo.basic.Constants.EXCHANGE_NAME;
import static com.learn.demo.basic.Constants.QUEUE_NAME;

public class FirstProducer {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = MQConnectionFactory.getConnection();
        Channel channel = connection.createChannel();

        Map<String,Object> params = new HashMap<>();

        params.put("参数名","参数值");
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT,true,false,params);

        channel.queueDeclare(QUEUE_NAME,true,false,false,params);

        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"key1");

        String message = "未使用交换机，直接到队列中";
        String message2 = "使用了路由键，发送了消息";
        for (int i =0;i<5;i++){
            channel.basicPublish("",QUEUE_NAME, MessageProperties.PERSISTENT_BASIC,message.getBytes());
            channel.basicPublish(EXCHANGE_NAME,"key1",MessageProperties.PERSISTENT_BASIC,message2.getBytes());
        }
        channel.close();
        connection.close();

    }
}

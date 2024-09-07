package com.learn.demo.basic;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

import static com.learn.demo.basic.Constants.EXCHANGE_NAME;
import static com.learn.demo.basic.Constants.QUEUE_NAME;

public class CallBackConsumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = MQConnectionFactory.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT,true,false,new HashMap<>());
        channel.queueDeclare(QUEUE_NAME,true,false,false,new HashMap<>());
        channel.basicQos(2);
        channel.queueDeclare(QUEUE_NAME,true,false,false,new HashMap<>());
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"key1");

        channel.basicConsume(QUEUE_NAME, new DeliverCallback() {
            @Override
            public void handle(String s, Delivery delivery) throws IOException {
                long deliverTag = delivery.getEnvelope().getDeliveryTag();
                String correlationId = delivery.getProperties().getCorrelationId();
                System.out.println("接收到了消费者标签"+s+" " + correlationId);
                //channel.basicAck(deliverTag,false);
            }
        }, new CancelCallback() {
            @Override
            public void handle(String s) throws IOException {
                System.out.println("队列被删除的回调");
            }
        }, new ConsumerShutdownSignalCallback() {
            @Override
            public void handleShutdownSignal(String s, ShutdownSignalException e) {
                System.out.println("链接关闭的回调");
            }
        });



    }
}

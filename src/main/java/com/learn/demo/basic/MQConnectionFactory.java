package com.learn.demo.basic;

import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MQConnectionFactory {
    private static final String host = "1.14.31.78";
    private static final String password = "zky521";
    private static final Integer port = 5672;
    private static final String user_name = "ZKY";
    private static final String virtual_host = "/mirror";


    public static Connection getConnection() throws IOException, TimeoutException {
        com.rabbitmq.client.ConnectionFactory factory = new com.rabbitmq.client.ConnectionFactory();
        factory.setHost(host);
        factory.setPassword(password);
        factory.setPort(port);
        factory.setUsername(user_name);
        factory.setVirtualHost(virtual_host);
        return factory.newConnection();
    }
}

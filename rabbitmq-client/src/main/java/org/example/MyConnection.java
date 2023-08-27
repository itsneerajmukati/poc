package org.example;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Neeraj Mukati
 */
public class MyConnection {
    private static ConnectionFactory factory;

    private MyConnection() {
        factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
    }

    public static MyConnection getInstance() {
        return ConnectionHolder.INSTANCE;
    }

    public Connection getConnection() throws IOException, TimeoutException {
        return factory.newConnection();
    }

    public ConnectionFactory getConnectionFactory() throws IOException, TimeoutException {
        return factory;
    }

    private static class ConnectionHolder {
        private static final MyConnection INSTANCE = new MyConnection();
    }

}

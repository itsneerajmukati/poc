package org.example.onetomany;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.example.MyConnection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Task {

    private static final String QUEUE_NAME="my-queue";
    public void send(String message) throws IOException, TimeoutException {
        Connection connection = MyConnection.getInstance().getConnection();
        try(Channel channel = connection.createChannel()) {
            boolean durable = false;
            boolean exclusive = false;
            boolean autoDelete = false;
            channel.queueDeclare(QUEUE_NAME,durable,exclusive,autoDelete,null);
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Channel closed");
        return;
    }
}

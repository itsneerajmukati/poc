package org.example.onetoone;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.example.MyConnection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Neeraj Mukati
 *
 * This is for simple one to one publish and consume.
 */
public class Sender {

    private static final String QUEUE_NAME="my-queue";
    public void send(String message) throws IOException, TimeoutException {
        Connection connection = MyConnection.getInstance().getConnection();
        try(Channel channel = connection.createChannel()) {
            boolean durable = false;
            boolean exclusive = false;
            boolean autoDelete = false;
            channel.queueDeclare(QUEUE_NAME,durable,exclusive,autoDelete,null);
            String exchange = ""; // empty means default exchange.
            channel.basicPublish(exchange, QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Channel closed");
        return;
    }
}

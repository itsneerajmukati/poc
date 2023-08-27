package org.example.pubsub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.example.MyConnection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Publish {

    private static final String EXCHANGE_NAME="my-exchange";
    public void send(String message) throws IOException, TimeoutException {
        Connection connection = MyConnection.getInstance().getConnection();
        try(Channel channel = connection.createChannel()) {
            String type = "fanout";
            channel.exchangeDeclare(EXCHANGE_NAME,type);
            String routingKey = ""; // in fanout it is ignored.
            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Channel closed");
        return;
    }
}

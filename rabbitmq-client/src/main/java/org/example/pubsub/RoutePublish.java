package org.example.pubsub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.example.MyConnection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RoutePublish {

    private static final String EXCHANGE_NAME="direct-exchange";
    public void send(String message,String routingKey) throws IOException, TimeoutException {
        Connection connection = MyConnection.getInstance().getConnection();
        try(Channel channel = connection.createChannel()) {
            String type = "direct";
            channel.exchangeDeclare(EXCHANGE_NAME,type);
            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Channel closed");
        return;
    }

}

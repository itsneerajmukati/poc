package org.example.pubsub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import org.example.MyConnection;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Subscriber {

    private static final String EXCHANGE_NAME="my-exchange";

    public void subscribe() throws IOException, TimeoutException {
        Connection connection = MyConnection.getInstance().getConnection();
        Channel channel = connection.createChannel();
        String type = "fanout";
        channel.exchangeDeclare(EXCHANGE_NAME,type);
        String queueName = channel.queueDeclare().getQueue();
        String bindingKey = "";
        channel.queueBind(queueName, EXCHANGE_NAME, bindingKey);
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            try {
                System.out.println(queueName + ":"+ message);
            } finally {

            }
        };
        /*
         if autoAck=true then once RabbitMQ delivers a message to the consumer, it immediately marks it for deletion.
         we loose message if consumer dies. Solution is manual message acknowledgment.
         */
        boolean autoAck = true;
        channel.basicConsume(queueName, autoAck,deliverCallback,cancelCallback -> {});
        return;
    }
}

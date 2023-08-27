package org.example.pubsub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import org.example.MyConnection;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class TopicSubscriber {

    private static final String EXCHANGE_NAME="topic-exchange";

    public void subscribe(String bindingKey) throws IOException, TimeoutException {
        Connection connection = MyConnection.getInstance().getConnection();
        Channel channel = connection.createChannel();
        String type = "topic";
        channel.exchangeDeclare(EXCHANGE_NAME,type);
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, bindingKey);
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            try {
                System.out.println(queueName + ":"+bindingKey+":"+ message);
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

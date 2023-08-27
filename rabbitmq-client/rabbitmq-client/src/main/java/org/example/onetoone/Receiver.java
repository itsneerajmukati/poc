package org.example.onetoone;

import com.rabbitmq.client.*;
import org.example.MyConnection;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author Neeraj Mukati
 *
 * This is for simple one to one publish and consume.
 */
public class Receiver {

    private static final String QUEUE_NAME="my-queue";

    public void receive() throws IOException, TimeoutException {
        Connection connection = MyConnection.getInstance().getConnection();
        Channel channel = connection.createChannel();
        boolean durable = false;
        /*
         * Exclusive queues may only be accessed by the current connection,
         * and are deleted when that connection closes.
         * Passive declaration of an exclusive queue by other connections are not allowed.
         */
        boolean exclusive = false;
        /*
        If set, the queue is deleted when all consumers have finished using it.
        The last consumer can be cancelled either explicitly or because its channel is closed.
        If there was no consumer ever on the queue, it won't be deleted.
        Applications can explicitly delete auto-delete queues using the Delete method as normal.
         */
        boolean autoDelete = false;
        /*
        Examples for map argument it is optional
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("x-message-ttl", 60000);
        args.put("x-expires", 1800000);
        Please read more options @ https://www.rabbitmq.com/queues.html#optional-arguments
         */
        channel.queueDeclare(QUEUE_NAME,durable,exclusive,autoDelete,null);
        //There are 2 option for receiver.
        // Option1
        /*DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(
                    String consumerTag,
                    Envelope envelope,
                    AMQP.BasicProperties properties,
                    byte[] body) throws IOException {
                String message = new String(body, StandardCharsets.UTF_8);
                System.out.println(message);
            }
        };
        channel.basicConsume( QUEUE_NAME, true, consumer);*/
        // Option2
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            try {
                System.out.println(message);
            } finally {
                System.out.println(" [x] Done");
            }
        };
        /*
         if autoAck=true then once RabbitMQ delivers a message to the consumer, it immediately marks it for deletion.
         we loose message if consumer dies. Solution is manual message acknowledgment.
         */
        boolean autoAck = true;
        channel.basicConsume(QUEUE_NAME, autoAck,deliverCallback,cancelCallback -> {});
        return;
    }
}

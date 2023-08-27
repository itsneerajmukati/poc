package org.example.onetomany;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import org.example.MyConnection;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Worker {

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
        // value 1 This tells RabbitMQ not to give more than one message to a worker at a time
        int prefetchCount = 1;
        channel.basicQos(prefetchCount);
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            try {
                try {
                    System.out.println(consumerTag);
                    doWork(message);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } finally {
                System.out.println(" [x] Done");
                boolean multiple = false;
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), multiple);
            }
        };
        boolean autoAck =false;
        channel.basicConsume(QUEUE_NAME, autoAck,deliverCallback,cancelCallback -> {});
        return;
    }
    private static void doWork(String task) throws InterruptedException {
        for (char ch: task.toCharArray()) {
            System.out.println("working for every dot wait for 2 sec");
            if (ch == '.') Thread.sleep(2000);
        }
    }
}

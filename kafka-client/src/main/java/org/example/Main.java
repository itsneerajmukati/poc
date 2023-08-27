package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;

public class Main {

    private static final Logger LOGGER= LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        LOGGER.info("Producer client started");
        ProducerClient producerClient = new ProducerClient();
        producerClient.topicsDetails();
        Thread.sleep(1000);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ConsumerClient consumerClient = new ConsumerClient();
                consumerClient.runConsumer("my-topic");
            }
        };
        //starting consumer
        new Thread(runnable).start();

        System.out.println("start putting messages");
        for(int i=0;i<10;i++) {
            Thread.sleep(1000);
            producerClient.send("my-topic", "order created"+i, "order details"+i);
        }

    }
}
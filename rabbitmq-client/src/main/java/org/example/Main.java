package org.example;

import org.example.onetomany.Task;
import org.example.onetomany.Worker;
import org.example.onetoone.Receiver;
import org.example.onetoone.Sender;
import org.example.pubsub.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Neeraj Mukati
 */
public class Main {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        System.out.println("Rabbit MQ Demo Clients");
        topicExchange();

    }

    private static void oneToOneQueque() throws IOException, TimeoutException {
        Sender sender = new Sender();
        sender.send("Hello fourth");
        Receiver receiver = new Receiver();
        receiver.receive();
    }

    private static void oneToManyConsumer() throws IOException, TimeoutException {
        Task task = new Task();
        for(int i=0;i<5;i++) {
            task.send("hello...");
        }
        Worker worker = new Worker();
        for(int i=0;i<2;i++) {
            //running 3 receivers
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        worker.receive();
                    } catch (IOException | TimeoutException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        }
    }

    private static void exchangeFanout() throws InterruptedException, IOException, TimeoutException {
        Subscriber subscriber = new Subscriber();
        for(int i=0;i<2;i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        subscriber.subscribe();
                    } catch (IOException | TimeoutException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        }
        //Wait for 1 sec to subscriber initializations.
        Thread.sleep(1000);
        Publish publish = new Publish();
        for(int i=0;i<5;i++) {
            publish.send("Hello worlds"+i);
        }
    }

    private static void direcrtExchange() throws IOException, TimeoutException, InterruptedException {
        RouteSubscriber routeSubscriber = new RouteSubscriber();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    routeSubscriber.subscribe("info");
                } catch (IOException | TimeoutException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    routeSubscriber.subscribe("error");
                } catch (IOException | TimeoutException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        //Wait for 1 sec to subscriber initializations.
        Thread.sleep(1000);
        RoutePublish routePublish = new RoutePublish();
        for(int i=0;i<5;i++) {
            routePublish.send("Hello info"+i,"info");
        }
        for(int i=0;i<5;i++) {
            routePublish.send("Hello error"+i,"error");
        }
    }

    private static void topicExchange() throws IOException, TimeoutException, InterruptedException {
        TopicSubscriber topicSubscriber = new TopicSubscriber();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    topicSubscriber.subscribe("app1.*");
                } catch (IOException | TimeoutException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    topicSubscriber.subscribe("app2.*");
                } catch (IOException | TimeoutException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    topicSubscriber.subscribe("*.error");
                } catch (IOException | TimeoutException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        //Wait for 1 sec to subscriber initializations.
        Thread.sleep(1000);
        TopicPublish topicPublish = new TopicPublish();
        for(int i=0;i<5;i++) {
            topicPublish.send("Hello app1 info"+i,"app1.info");
        }
        for(int i=0;i<5;i++) {
            topicPublish.send("Hello app1 error"+i,"app1.error");
        }
        for(int i=0;i<5;i++) {
            topicPublish.send("Hello app2 info"+i,"app2.info");
        }
        for(int i=0;i<5;i++) {
            topicPublish.send("Hello app2 error"+i,"app2.error");
        }
    }
}
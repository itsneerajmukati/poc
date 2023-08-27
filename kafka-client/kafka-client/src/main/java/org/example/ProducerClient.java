package org.example;

import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class ProducerClient {

    private static KafkaProducer<String,String> producer;
    private static Admin admin;

    public ProducerClient() {
        try(InputStream input = ProducerClient.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            producer = new KafkaProducer<>(properties);
            admin = Admin.create(properties);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void topicsDetails() throws ExecutionException, InterruptedException {
        admin.listTopics().names().get().forEach(System.out::println);
    }

    public void createTopic(String topicName) {
        int partition=1;
        short replica = 1;
        NewTopic newTopic = new NewTopic(topicName,partition, replica);
        admin.createTopics(Collections.singleton(newTopic));
    }

    public void send(String topic, String key, String value) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic,key,value);
        producer.send(record);
    }

    public static KafkaProducer<String,String> getProducer() {
        return producer;
    }
}

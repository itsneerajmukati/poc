package org.example;

import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class ConsumerClient {

    private static KafkaConsumer<String,String> consumer;
    public ConsumerClient() {
        try(InputStream input = ProducerClient.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            consumer = new KafkaConsumer<>(properties);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void runConsumer(String topicName) {
        consumer.subscribe(Collections.singleton(topicName));
        while(true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
            if(records.isEmpty()) {
                System.out.println("No message found");
            }
            for(ConsumerRecord<String,String> record : records) {
                System.out.println(
                        "offset:" + record.offset()
                        +"key:"+record.key()
                        +"value:"+record.value()
                );
            }
        }
    }
}

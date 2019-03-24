package cn.mrerror.yinuoc.kafka;

import org.apache.kafka.clients.producer.Producer;

import java.util.Properties;
import java.util.Scanner;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 生产者JAVA版
 */
public class KafkaJavaProducer {

    private static Scanner scanner;
    private Producer<String, String> producer;
    private Properties props;

    @Before
    public void init() {
        props = new Properties();
        props.put("bootstrap.servers", "hadoop1:9092,hadoop2:9092,hadoop3:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    }

    @Test
    public void produce() {
        System.out.println("begin produce");
        connectionKafka();
        System.out.println("finish produce");
    }

    public void connectionKafka() {
        producer = new KafkaProducer<>(props);
        scanner = new Scanner(System.in);

        int i=0;
        while (true) {
            String sendContent=++i+"yinuoc";
            producer.send(new ProducerRecord<String, String>("news", sendContent));
            System.out.println(sendContent);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @After
    public void destroy() {
        producer.close();
    }
}
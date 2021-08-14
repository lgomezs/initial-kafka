package pe.kafka.demo.thread;

import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadConsumer {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:9092");
        props.setProperty("group.id", "devs4j-group");
        props.setProperty("enable.auto.commit", "true");
        props.setProperty("auto.commit.interval.ms", "1000");
        props.setProperty("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");

        for (int i = 0; i < 5; i++) {
            Runnable worker = new ThreadConsumer(new KafkaConsumer<>(props));
            executorService.execute(worker);
        }

        executorService.shutdown();
        while (!executorService.isTerminated()) ;
    }
}

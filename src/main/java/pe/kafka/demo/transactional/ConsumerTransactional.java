package pe.kafka.demo.transactional;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@Slf4j
public class ConsumerTransactional {

    public static void main(String[] args) {
        String topicName = "devs4j-topic";

        //Si no hay ningún error, los mensajes en el topic pasarán de read_uncommitted a read_committed y serán leidos por  el consumer.

        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:29092");
        props.setProperty("group.id", "devs4j-group");
        props.setProperty("enable.auto.commit", "true");
        props.setProperty("isolation.level", "read_committed");
        props.setProperty("auto.commit.interval.ms", "1000");
        props.setProperty("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(Collections.singletonList(topicName));
            while (true) {
                ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ZERO);

                for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {

                    log.info("key , value : {} {} ", consumerRecord.key(), consumerRecord.value());
                }
            }
        }
    }
}

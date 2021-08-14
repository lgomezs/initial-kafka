package pe.kafka.demo.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

@Slf4j
public class ProducerKafka {

    public static void main(String[] args) {
        String topicName = "devs4j-topic";
        String message = "message";

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("linger.ms", "10");

        //send async
        try (Producer<String, String> producerKafka = new KafkaProducer<>(props);) {
            for (int cantRGe = 0; cantRGe < 100; cantRGe++)
                producerKafka.send(new ProducerRecord<>(topicName, String.valueOf(cantRGe), message + cantRGe));
        }

        //send sync
     /*   try (Producer<String, String> producerKafka = new KafkaProducer<>(props);) {
            try {
                for (int cantRGe = 0; cantRGe < 1000; cantRGe++) {
                    producerKafka.send(new ProducerRecord<>(topicName, String.valueOf(cantRGe), message + cantRGe)).get();
                }
                producerKafka.flush();
            } catch (InterruptedException | ExecutionException e) {
                log.error("Erro producer :  {} ", e.getMessage());
                e.printStackTrace();
            }
        }*/
    }
}

package pe.kafka.demo.callback;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.*;

import java.util.Properties;

public class CallbackProducer {

    public static void main(String[] args) {
        String topicName = "devs4j-topic";
        String message = "message";

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:29092");
        props.put("acks", "all");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("linger.ms", "10");

        //send async
        try (Producer<String, String> producerKafka = new KafkaProducer<>(props);) {
            for (int cantRGe = 0; cantRGe < 1000; cantRGe++)
                producerKafka.send(new ProducerRecord<>(topicName, String.valueOf(cantRGe), message + cantRGe), new DemoCallback());
        }
    }

}

@Slf4j
class DemoCallback implements Callback {
    @Override
    public void onCompletion(RecordMetadata recordMetadata, Exception exception) {
        if (exception != null)
            log.error("error onCompletion {} {} :  ", exception.getMessage(), exception);
        log.info("partition topic : {} {} ", recordMetadata.partition(), recordMetadata.topic());
    }
}



package pe.kafka.demo.transactional;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

@Slf4j
public class ProducerTransactional {

    public static void main(String[] args) {
        String topicName = "devs4j-topic";
        String message = "message";

      //  config Producer transactional
          //      -Definir acks =all
            //    -Asignar un transactional id
        // transactional.id=id-producer

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:29092");
        props.put("acks", "all");
        props.put("transactional.id", "id-producer");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("linger.ms", "10");

        //send async
        try (Producer<String, String> producerKafka = new KafkaProducer<>(props);) {
            try {
                producerKafka.initTransactions();
                producerKafka.beginTransaction();

                for (int cantRGe = 0; cantRGe < 10000; cantRGe++) {
                    producerKafka.send(new ProducerRecord<>(topicName, String.valueOf(cantRGe), message + cantRGe));

                    if (cantRGe == 500)
                        throw new Exception("abort transaction");
                }

                producerKafka.commitTransaction();
                producerKafka.flush();

            } catch (Exception ex) {
                log.error("error producer {} {}: ", ex.getMessage(), ex);
                producerKafka.abortTransaction();
            }
        }
    }
}

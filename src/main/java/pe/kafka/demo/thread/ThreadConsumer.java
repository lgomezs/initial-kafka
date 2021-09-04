package pe.kafka.demo.thread;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;

import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class ThreadConsumer extends Thread {

    private final KafkaConsumer<String, String> consumer;
    private final AtomicBoolean closed = new AtomicBoolean(false);
    private static final String TOPIC_NAME = "devs4j-topic";

    public ThreadConsumer(KafkaConsumer<String, String> kafkaConsumer) {
        this.consumer = kafkaConsumer;
    }

    @Override
    public void run() {
        try {
            consumer.subscribe(Collections.singletonList(TOPIC_NAME));
            while (!closed.get()) {
                ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ZERO);
                for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
                    log.info("key , value : {} {} ", consumerRecord.key(), consumerRecord.value());
                }
            }
        } catch (WakeupException e) {
            if (!closed.get())
                throw e;
        } finally {
            consumer.close();
        }
    }

    public void shutdown() {
        closed.set(true);
        consumer.wakeup();
    }
}

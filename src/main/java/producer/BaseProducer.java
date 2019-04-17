package producer;

import java.util.Properties;
import java.util.concurrent.Future;

import constants.KafkaConstants;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class BaseProducer {
    protected  Properties props;
    protected  Producer<String, String> producer;

    public BaseProducer(String brokerString) {
        props = new Properties();
        props.put("bootstrap.servers", brokerString);
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", KafkaConstants.KAFKA_KEY_SERIALIZER);
        props.put("value.serializer", KafkaConstants.KAFKA_VALUE_SERIALIZER);
        producer = new KafkaProducer<>(props);
    }

    public Future<RecordMetadata> sendMessage(String topic, String message) {

        Future<RecordMetadata> future = producer.send(new ProducerRecord<>(topic, message));
        return future;
    }

    public void sendMessage(String topic, String key, String value) {
        producer.send(new ProducerRecord<>(topic, key, value));
    }

    public void closeProducer() {
        producer.close();
    }

    public static void main(String[] args) {
        BaseProducer kafkaProducer = new BaseProducer(KafkaConstants.KAFKA_BROKER_STRING);

        /* Sending messages without a key */
        for (int i = 0; i < 10; i++)
            kafkaProducer.sendMessage(KafkaConstants.KAFKA_TOPIC, i + "mA");

        /* Sending messages without a key */
        for (int i = 0; i < 10; i++) {
            kafkaProducer.sendMessage(KafkaConstants.KAFKA_TOPIC, "Key : " + i, "Message : " + i);
        }

        kafkaProducer.closeProducer();
    }
}

package ru.ycan.client.service.config;

import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ycan.client.service.config.props.KafkaProperties;
import ru.ycan.libs.avro.schemas.ProductAvro;

import java.util.Collections;
import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfig {

    private final KafkaProperties properties;

    @Bean
    public KafkaConsumer<String, ProductAvro> kafkaConsumer() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.consumer().bootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "recommendation-product-consumer-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, properties.consumer().keyDeserializer());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, properties.consumer().valueDeserializer());

        props.put(KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG, properties.consumer().schemaRegistryUrl());
        props.put("specific.avro.reader", properties.consumer().specificAvroReader());

        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        var consumer = new KafkaConsumer<String, ProductAvro>(props);
        consumer.subscribe(Collections.singletonList(properties.topics().in()));
        return consumer;
    }
}

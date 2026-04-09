package ru.ycan.client.service.config.props;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kafka")
public record KafkaProperties(Consumer consumer,
                              Topics topics) {
    public record Consumer(@JsonProperty(value = "bootstrap-servers") String bootstrapServers,
                           @JsonProperty(value = "key-deserializer") String keyDeserializer,
                           @JsonProperty(value = "value-deserializer") String valueDeserializer,
                           @JsonProperty(value = "schema-registry-url") String schemaRegistryUrl,
                           @JsonProperty(value = "specific-avro-reader") String specificAvroReader,
                           String acks, String retries) {}
    public record Topics(String in, String out) {}
}

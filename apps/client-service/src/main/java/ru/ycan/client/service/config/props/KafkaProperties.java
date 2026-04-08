package ru.ycan.client.service.config.props;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kafka")
public record KafkaProperties(@JsonProperty(value = "bootstrap-servers") String bootstrapServers,
                              Producer producer,
                              Consumer consumer,
                              Topics topics) {
    public record Producer(@JsonProperty(value = "key-serializer") String keySerializer,
                           @JsonProperty(value = "value-serializer") String valueSerializer) {}

    public record Consumer(@JsonProperty(value = "key-deserializer") String keyDeserializer,
                           @JsonProperty(value = "value-deserializer") String valueDeserializer) {}

    public record Topics(String in, String out) {}
}

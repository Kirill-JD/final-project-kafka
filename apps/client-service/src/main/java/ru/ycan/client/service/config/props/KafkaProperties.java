package ru.ycan.client.service.config.props;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kafka")
public record KafkaProperties(@JsonProperty(value = "bootstrap-servers") String bootstrapServers,
                              Producer producer,
                              Topic topic) {
    public record Producer(@JsonProperty(value = "key-serializer") String keySerializer,
                           @JsonProperty(value = "value-serializer") String valueSerializer) {}

    public record Topic(String out) {}
}

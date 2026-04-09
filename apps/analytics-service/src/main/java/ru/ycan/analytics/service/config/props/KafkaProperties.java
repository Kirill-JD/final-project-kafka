package ru.ycan.analytics.service.config.props;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kafka")
public record KafkaProperties(@JsonProperty(value = "bootstrap-servers") String bootstrapServers,
                              Topic topics) {

    public record Topic(String in, String out) {}
}

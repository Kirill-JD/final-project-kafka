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
                           String acks, String retries, Ssl ssl) {}
    public record Topics(String in, String out) {}

    public record Ssl(
            @JsonProperty(value = "security-protocol") String securityProtocol,
            @JsonProperty(value = "truststore-location") String truststoreLocation,
            @JsonProperty(value = "truststore-password") String truststorePassword,
            @JsonProperty(value = "keystore-location") String keystoreLocation,
            @JsonProperty(value = "keystore-password") String keystorePassword,
            @JsonProperty(value = "key-password") String keyPassword
    ) {}
}

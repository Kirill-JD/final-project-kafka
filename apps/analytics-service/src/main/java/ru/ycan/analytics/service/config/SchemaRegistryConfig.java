package ru.ycan.analytics.service.config;

import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchemaRegistryConfig {
    @Bean
    public SchemaRegistryClient schemaRegistryClient(
            @Value("${spring.kafka.properties.schema.registry.url}") String url) {
        return new CachedSchemaRegistryClient(url, 100);
    }
}

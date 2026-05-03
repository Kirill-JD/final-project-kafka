package ru.ycan.analytics.service.service;

import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchemaRegistryService {
    private final SchemaRegistryClient client;

    public String getLatestSchema(String topic) {
        try {
            var schemaMetadata = client.getLatestSchemaMetadata("%s-value".formatted(topic));
            return schemaMetadata.getSchema();
        } catch (Exception e) {
            log.error("Не удалось получить схему", e);
            throw new IllegalStateException(e);
        }
    }
}

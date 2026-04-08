package ru.ycan.client.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions;
import ru.ycan.client.service.helpers.DoubleToZonedDateTimeConverter;

import java.util.List;

@Configuration
public class ElasticsearchConfig {

    @Bean
    public ElasticsearchCustomConversions elasticsearchCustomConversions(DoubleToZonedDateTimeConverter converter) {
        return new ElasticsearchCustomConversions(List.of(converter));
    }
}

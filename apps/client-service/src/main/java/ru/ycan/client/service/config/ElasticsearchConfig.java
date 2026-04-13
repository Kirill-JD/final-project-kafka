package ru.ycan.client.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions;

import java.time.ZonedDateTime;
import java.util.List;

@Configuration
public class ElasticsearchConfig {

    @Bean
    public ElasticsearchCustomConversions elasticsearchCustomConversions() {
        return new ElasticsearchCustomConversions(List.of(
                new StringToZonedDateTimeConverter()
        ));
    }

    @ReadingConverter
    static class StringToZonedDateTimeConverter implements Converter<String, ZonedDateTime> {
        @Override
        public ZonedDateTime convert(String source) {
            return ZonedDateTime.parse(source);
        }
    }
}

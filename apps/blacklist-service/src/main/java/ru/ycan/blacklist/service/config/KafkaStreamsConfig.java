package ru.ycan.blacklist.service.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import ru.ycan.blacklist.service.config.props.KafkaTopicProperties;
import ru.ycan.libs.avro.schemas.ProductAvro;

@Configuration
@EnableKafkaStreams
@RequiredArgsConstructor
public class KafkaStreamsConfig {
    private final KafkaTopicProperties properties;

    @Bean
    public KStream<String, ProductAvro> filteringStream(StreamsBuilder builder) {
        var globalTable = builder.globalTable(properties.global(), Consumed.with(Serdes.String(), Serdes.Boolean()));
        KStream<String, ProductAvro> stream = builder.stream(properties.in());

        var filteredStream = stream.leftJoin(globalTable,
                                             (key, value) -> value.getName(),
                                             (product, isBlacklisted) ->
                                                     Boolean.TRUE.equals(isBlacklisted) ? null : product)
                                   .filter((k, v) -> v != null);

        filteredStream.to(properties.out());
        return filteredStream;
    }
}

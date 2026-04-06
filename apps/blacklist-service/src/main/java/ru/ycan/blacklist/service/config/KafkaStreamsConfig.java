package ru.ycan.blacklist.service.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.support.serializer.JsonSerde;
import ru.ycan.blacklist.service.config.props.KafkaProperties;
import ru.ycan.kafka.helper.pojo.ProductDto;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafkaStreams
@RequiredArgsConstructor
public class KafkaStreamsConfig {
    private final KafkaProperties properties;

    @Bean(name = "defaultKafkaStreamsConfig")
    public KafkaStreamsConfiguration kafkaStreamsConfiguration() {
        Map<String, Object> props = new HashMap<>();
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, properties.bootstrapServers());
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "blacklist-app");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.Boolean().getClass().getName());
        return new KafkaStreamsConfiguration(props);
    }

    @Bean
    public KStream<?, ?> filteringStream(StreamsBuilder builder) {
        // Создание GlobalKTable
        GlobalKTable<String, Boolean> globalTable = builder.globalTable(
                properties.topics().global(), // название топика
                Consumed.with(Serdes.String(), Serdes.Boolean()) // сериализация/десериализация
        );

        KStream<String, ProductDto> stream = builder.stream(
                properties.topics().in(),
                Consumed.with(Serdes.String(), productDtoSerde())
        );

        var filteredStream = stream.leftJoin(globalTable,
                                             (key, value) -> value.name(),
                                             (product, isBlacklisted) ->
                                                     Boolean.TRUE.equals(isBlacklisted) ? null : product)
                                   .filter((k, v) -> v != null);

        filteredStream.to(properties.topics().out(),
                          Produced.with(Serdes.String(), productDtoSerde())
        );

        return filteredStream;
    }

    @Bean
    public JsonSerde<ProductDto> productDtoSerde() {
        return new JsonSerde<>(ProductDto.class);
    }
}

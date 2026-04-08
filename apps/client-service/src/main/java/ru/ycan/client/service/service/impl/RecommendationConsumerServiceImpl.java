package ru.ycan.client.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.springframework.stereotype.Service;
import ru.ycan.client.service.service.RecommendationConsumerService;
import ru.ycan.kafka.helper.pojo.ProductDto;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RecommendationConsumerServiceImpl implements RecommendationConsumerService {
    private static final int LIMIT_RECOMMENDATION = 5;

    private final KafkaConsumer<String, ProductDto> consumer;


    public List<ProductDto> getRecommendationProducts() {
        List<ProductDto> result = new ArrayList<>();
        Map<TopicPartition, OffsetAndMetadata> offsetsToCommit = new HashMap<>();

        ConsumerRecords<String, ProductDto> records = consumer.poll(Duration.ofSeconds(10));
        for (ConsumerRecord<String, ProductDto> record : records) {
            result.add(record.value());
            offsetsToCommit.put(new TopicPartition(record.topic(), record.partition()),
                                new OffsetAndMetadata(record.offset() + 1));

            if (result.size() >= LIMIT_RECOMMENDATION) {
                break;
            }
        }
        consumer.commitSync(offsetsToCommit);
        return result;
    }
}

package ru.ycan.shop.producer.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.ycan.libs.avro.schemas.ProductAvro;
import ru.ycan.shop.producer.config.props.KafkaTopicProperties;
import ru.ycan.shop.producer.service.ProductProducerService;

import java.util.List;

import static ru.ycan.shop.producer.messages.Messages.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductProducerServiceImpl implements ProductProducerService {

    private final KafkaTemplate<String, ProductAvro> kafkaTemplate;
    private final KafkaTopicProperties properties;

    @Override
    public void sendRecords(List<ProductAvro> productsAvro) {
        try {
            productsAvro.forEach(this::send);
            kafkaTemplate.flush();
            log.info(INFO_SUCCESS_SEND_ALL_MESSAGES_KAFKA.getValue(), properties.out());
        } catch (Exception e) {
            log.error(ERROR_SEND_MESSAGES_KAFKA.getValue(), properties.out(), e);
        }
    }

    private void send(ProductAvro productAvro) {
        kafkaTemplate.send(properties.out(), productAvro.getProductId(), productAvro);
        log.info(INFO_SEND_MESSAGE_KAFKA.getValue(), properties.out(), productAvro.getProductId(), productAvro);
    }
}

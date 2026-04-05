package ru.ycan.shop.producer.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.ycan.kafka.helper.pojo.Product;
import ru.ycan.shop.producer.config.props.KafkaProperties;
import ru.ycan.shop.producer.service.ProductProducerService;

import java.util.List;

import static ru.ycan.shop.producer.messages.Messages.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductProducerServiceImpl implements ProductProducerService {

    private final KafkaProperties properties;
    private final KafkaTemplate<String, Product> kafkaTemplate;

    @Override
    public void sendRecords(List<Product> products) {
        try {
            products.forEach(this::send);
            kafkaTemplate.flush();
            log.info(INFO_SUCCESS_SEND_ALL_MESSAGES_KAFKA.getValue(), properties.topic().out());
        } catch (Exception e) {
            log.error(ERROR_SEND_MESSAGES_KAFKA.getValue(), properties.topic().out(), e);
        }
    }

    private void send(Product product) {
        kafkaTemplate.send(properties.topic().out(), product.productId(), product);
        log.info(INFO_SEND_MESSAGE_KAFKA.getValue(), properties.topic().out(), product.productId(), product);
    }
}

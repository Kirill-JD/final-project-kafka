package ru.ycan.shop.producer.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.ycan.kafka.helper.pojo.ProductDto;
import ru.ycan.shop.producer.config.props.KafkaProperties;
import ru.ycan.shop.producer.service.ProductProducerService;

import java.util.List;

import static ru.ycan.shop.producer.messages.Messages.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductProducerServiceImpl implements ProductProducerService {

    private final KafkaProperties properties;
    private final KafkaTemplate<String, ProductDto> kafkaTemplate;

    @Override
    public void sendRecords(List<ProductDto> productDtos) {
        try {
            productDtos.forEach(this::send);
            kafkaTemplate.flush();
            log.info(INFO_SUCCESS_SEND_ALL_MESSAGES_KAFKA.getValue(), properties.topic().out());
        } catch (Exception e) {
            log.error(ERROR_SEND_MESSAGES_KAFKA.getValue(), properties.topic().out(), e);
        }
    }

    private void send(ProductDto productDto) {
        kafkaTemplate.send(properties.topic().out(), productDto.productId(), productDto);
        log.info(INFO_SEND_MESSAGE_KAFKA.getValue(), properties.topic().out(), productDto.productId(), productDto);
    }
}

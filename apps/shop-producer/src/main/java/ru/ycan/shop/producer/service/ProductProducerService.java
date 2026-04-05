package ru.ycan.shop.producer.service;

import ru.ycan.kafka.helper.pojo.ProductDto;

import java.util.List;

public interface ProductProducerService {
    void sendRecords(List<ProductDto> productDto);
}

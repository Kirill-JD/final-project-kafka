package ru.ycan.shop.producer.service;

import ru.ycan.kafka.helper.pojo.ProductDto;

import java.util.List;

public interface FileReadService {
    List<ProductDto> loadProductDataFromFile();
}

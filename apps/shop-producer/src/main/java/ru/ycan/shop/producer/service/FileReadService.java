package ru.ycan.shop.producer.service;

import ru.ycan.libs.common.pojo.ProductDto;

import java.util.List;

public interface FileReadService {
    List<ProductDto> loadProductDataFromFile();
}

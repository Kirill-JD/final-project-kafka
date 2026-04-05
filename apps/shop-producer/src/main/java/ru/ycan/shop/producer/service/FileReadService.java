package ru.ycan.shop.producer.service;

import ru.ycan.kafka.helper.pojo.Product;

import java.util.List;

public interface FileReadService {
    List<Product> loadProductDataFromFile();
}

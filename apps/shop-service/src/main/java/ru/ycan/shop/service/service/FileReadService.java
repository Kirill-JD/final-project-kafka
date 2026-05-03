package ru.ycan.shop.service.service;

import ru.ycan.libs.avro.schemas.ProductAvro;

import java.util.List;

public interface FileReadService {
    List<ProductAvro> loadProductDataFromFile();
}

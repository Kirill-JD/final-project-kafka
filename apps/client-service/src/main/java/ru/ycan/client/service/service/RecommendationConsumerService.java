package ru.ycan.client.service.service;

import ru.ycan.libs.avro.schemas.ProductAvro;

import java.util.List;

public interface RecommendationConsumerService {
    List<ProductAvro> getRecommendationProducts();
}

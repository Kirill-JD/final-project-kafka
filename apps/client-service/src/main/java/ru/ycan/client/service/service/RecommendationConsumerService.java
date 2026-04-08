package ru.ycan.client.service.service;

import ru.ycan.kafka.helper.pojo.ProductDto;

import java.util.List;

public interface RecommendationConsumerService {
    List<ProductDto> getRecommendationProducts();
}

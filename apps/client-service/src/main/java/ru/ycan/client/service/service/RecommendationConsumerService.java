package ru.ycan.client.service.service;

import ru.ycan.libs.common.pojo.ProductDto;

import java.util.List;

public interface RecommendationConsumerService {
    List<ProductDto> getRecommendationProducts();
}

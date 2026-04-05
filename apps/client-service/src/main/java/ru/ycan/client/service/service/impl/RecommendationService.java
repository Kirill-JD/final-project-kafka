package ru.ycan.client.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ycan.client.service.mapper.ProductMapper;
import ru.ycan.client.service.pojo.ClientRequestEvent;
import ru.ycan.client.service.repository.RecommendationRepository;

import java.time.ZonedDateTime;

import static ru.ycan.client.service.pojo.EventTypes.RECOMMENDATION;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final ClientEventProducer producer;
    private final RecommendationRepository repository;
    private final ProductMapper mapper;

    public void getRecommendations(String userId) {
        var optionalRecommendation = repository.findByUserId(userId);

        // Создаем событие
        ClientRequestEvent event = new ClientRequestEvent(userId, RECOMMENDATION, ZonedDateTime.now());
        producer.sendEvent(event);

        optionalRecommendation.ifPresentOrElse(
                r -> System.out.println("Рекомендации: " + mapper.toProductsResponse(r.getProducts())),
                () -> System.out.println("Не смогли найти для Вас рекомендации"));
    }
}

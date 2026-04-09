package ru.ycan.client.service.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ycan.client.service.mapper.ProductMapper;
import ru.ycan.client.service.repository.ProductRepository;
import ru.ycan.client.service.service.ClientEventProducerService;
import ru.ycan.client.service.service.RecommendationConsumerService;
import ru.ycan.libs.avro.schemas.ClientRequestEvent;

import java.time.ZonedDateTime;

import static ru.ycan.libs.avro.schemas.EventTypes.RECOMMENDATION;
import static ru.ycan.libs.avro.schemas.EventTypes.SEARCH;

@Component
@RequiredArgsConstructor
public class Handler {
    private final RecommendationConsumerService consumer;
    private final ClientEventProducerService producer;
    private final ProductRepository repository;
    private final ProductMapper mapper;

    public void getRecommendations(String userId) {
        var recommendationProducts = consumer.getRecommendationProducts();

        // Создаем событие
        ClientRequestEvent event = ClientRequestEvent.newBuilder()
                                                     .setUserId(userId)
                                                     .setEventType(RECOMMENDATION)
                                                     .setEventTime(ZonedDateTime.now().toInstant())
                                                     .build();
        producer.sendEvent(event);

        if (recommendationProducts.isEmpty()) {
            System.out.println("Не смогли найти для Вас рекомендации");
        } else {
            System.out.println("Рекомендации: " + recommendationProducts);
        }
    }

    public void searchProduct(String userId, String productName) {
        var products = repository.searchByName(productName);
        ClientRequestEvent event = ClientRequestEvent.newBuilder()
                                                     .setUserId(userId)
                                                     .setEventType(SEARCH)
                                                     .setQueryBody(productName)
                                                     .setEventTime(ZonedDateTime.now().toInstant())
                                                     .build();
        producer.sendEvent(event);

        if (products.isEmpty()) {
            System.out.printf("Не смогли найти товар '%s'\n", productName);
        } else {
            System.out.println("Найденные товары: " + mapper.toProductsResponse(products));
        }
    }
}

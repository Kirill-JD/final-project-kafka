package ru.ycan.client.service.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.ycan.client.service.mapper.ProductMapper;
import ru.ycan.client.service.pojo.ClientRequestEvent;
import ru.ycan.client.service.repository.ProductRepository;
import ru.ycan.client.service.service.ProductSearchService;

import java.time.ZonedDateTime;

import static ru.ycan.client.service.pojo.EventTypes.SEARCH;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductSearchServiceImpl implements ProductSearchService {

    private final ClientEventProducer producer;
    private final ProductRepository repository;
    private final ProductMapper mapper;

    public void searchProduct(String userId, String productName) {
        var products = repository.findByNameContainingIgnoreCase(productName);

        // Отправляем событие поиска в Kafka
        ClientRequestEvent event = new ClientRequestEvent(userId, SEARCH, ZonedDateTime.now());
        producer.sendEvent(event);

        if (products.isEmpty()) {
            System.out.printf("Не смогли найти товар '%s'\n", productName);
        } else {
            System.out.println("Найденные товары: " + mapper.toProductsResponse(products));
        }
    }
}

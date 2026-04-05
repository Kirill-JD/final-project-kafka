package ru.ycan.shop.producer.handler;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ycan.shop.producer.service.FileReadService;
import ru.ycan.shop.producer.service.ProductProducerService;

@Component
@RequiredArgsConstructor
public class Handler {
    private final FileReadService fileReadService;
    private final ProductProducerService productProducerService;

    @PostConstruct
    public void loadDataInKafkaFromFile() {
        var products = fileReadService.loadProductDataFromFile();
        productProducerService.sendRecords(products);
    }
}

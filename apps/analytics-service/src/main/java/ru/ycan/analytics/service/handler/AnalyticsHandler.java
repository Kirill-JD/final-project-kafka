package ru.ycan.analytics.service.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.ycan.analytics.service.service.AnalyticsService;
import ru.ycan.analytics.service.service.RecommendationProducerService;

@Slf4j
@Component
@RequiredArgsConstructor
public class AnalyticsHandler {
    private final AnalyticsService analyticsService;
    private final RecommendationProducerService producerService;

    public void analyticsProcess() {
        analyticsService.saveDataInHdfs();
        log.info("Всё сохранили");
        var recommendationProducts = analyticsService.getRecommendationProducts();
        log.info("Всё получили");
        producerService.sendRecords(recommendationProducts);
        log.info("Всё отправили");
    }
}

package ru.ycan.blacklist.service.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.ycan.blacklist.service.config.props.KafkaProperties;

import java.util.List;

import static ru.ycan.blacklist.service.messages.Messages.ERROR_SEND_MESSAGES_KAFKA;
import static ru.ycan.blacklist.service.messages.Messages.INFO_SEND_MESSAGE_KAFKA;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlacklistProductNameProducer {
    private static final List<String> INIT_TEST_DATA = List.of("Кетчуп Балтимор");

    private final KafkaTemplate<String, Boolean> kafkaTemplate;
    private final KafkaProperties properties;

    // Чисто тестовый метод для проверки фильтра
    @PostConstruct
    public void initTestData() {
        INIT_TEST_DATA.forEach(this::addProductNameInBlacklist);
    }


    public void addProductNameInBlacklist(String productName) {
        try {
            kafkaTemplate.send(properties.topics().global(), productName, true).get();
            log.info(INFO_SEND_MESSAGE_KAFKA.getValue(), properties.topics().global(), productName, true);
            System.out.printf("Продукт '%s' успешно заблокирован%n", productName);
        } catch (Exception e) {
            log.error(ERROR_SEND_MESSAGES_KAFKA.getValue(), properties.topics().global(), e);
        }
    }
}

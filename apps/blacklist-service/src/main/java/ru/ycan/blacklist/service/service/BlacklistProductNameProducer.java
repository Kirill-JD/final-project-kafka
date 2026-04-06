package ru.ycan.blacklist.service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.ycan.blacklist.service.config.props.KafkaProperties;

import static ru.ycan.blacklist.service.messages.Messages.ERROR_SEND_MESSAGES_KAFKA;
import static ru.ycan.blacklist.service.messages.Messages.INFO_SEND_MESSAGE_KAFKA;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlacklistProductNameProducer {

    private final KafkaTemplate<String, Boolean> kafkaTemplate;
    private final KafkaProperties properties;


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

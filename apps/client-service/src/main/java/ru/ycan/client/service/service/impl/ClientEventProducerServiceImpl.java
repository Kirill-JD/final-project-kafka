package ru.ycan.client.service.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.ycan.client.service.config.props.KafkaProperties;
import ru.ycan.client.service.pojo.ClientRequestEvent;
import ru.ycan.client.service.service.ClientEventProducerService;

import static ru.ycan.client.service.messages.Messages.ERROR_SEND_MESSAGES_KAFKA;
import static ru.ycan.client.service.messages.Messages.INFO_SEND_MESSAGE_KAFKA;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientEventProducerServiceImpl implements ClientEventProducerService {

    private final KafkaTemplate<String, ClientRequestEvent> kafkaTemplate;
    private final KafkaProperties properties;


    public void sendEvent(ClientRequestEvent event) {
        try {
            kafkaTemplate.send(properties.topics().out(), event.userId(), event).get();
            log.info(INFO_SEND_MESSAGE_KAFKA.getValue(), properties.topics().out(), event.userId(), event);
        } catch (Exception e) {
            log.error(ERROR_SEND_MESSAGES_KAFKA.getValue(), properties.topics().out(), e);
        }
    }
}

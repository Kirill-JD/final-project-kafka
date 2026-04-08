package ru.ycan.client.service.service;

import ru.ycan.client.service.pojo.ClientRequestEvent;

public interface ClientEventProducerService {
    void sendEvent(ClientRequestEvent event);
}

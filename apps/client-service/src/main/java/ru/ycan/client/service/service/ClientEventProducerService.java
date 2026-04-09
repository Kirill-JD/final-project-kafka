package ru.ycan.client.service.service;


import ru.ycan.libs.avro.schemas.ClientRequestEvent;

public interface ClientEventProducerService {
    void sendEvent(ClientRequestEvent event);
}

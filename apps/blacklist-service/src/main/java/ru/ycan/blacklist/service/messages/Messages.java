package ru.ycan.blacklist.service.messages;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Messages {
    INFO_SEND_MESSAGE_KAFKA("Сообщение отправлено в kafka. Topic: '{}', key: '{}', value: '{}'"),
    ERROR_SEND_MESSAGES_KAFKA("Не удалось отправить все сообщения в топик: '{}'"),
    ;

    private final String value;
}

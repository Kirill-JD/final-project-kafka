package ru.ycan.client.service.messages;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Messages {
    INFO_START_FILE_READ("Начинаем вычитывать данные из файла: {}"),
    INFO_SUCCESS_FILE_READ("Все данные успешно получены из файла, '{}'"),
    INFO_SEND_MESSAGE_KAFKA("Сообщение отправлено в kafka. Topic: '{}', key: '{}', value: '{}'"),
    INFO_SUCCESS_SEND_ALL_MESSAGES_KAFKA("Все сообщения отправлены в топик: '{}'"),
    ERROR_FILE_READ("Не удалось прочитать данные о продуктах из файла"),
    ERROR_SEND_MESSAGES_KAFKA("Не удалось отправить все сообщения в топик: '{}'"),
    ;

    private final String value;
}

package ru.ycan.client.service.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

public record ClientRequestEvent(@JsonProperty(value = "user_id") String userId,
                                 @JsonProperty(value = "event_type") EventTypes eventType,
                                 @JsonProperty(value = "event_time") ZonedDateTime eventTime) {
}

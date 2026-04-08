package ru.ycan.client.service.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
public record ClientRequestEvent(@JsonProperty(value = "user_id", required = true) String userId,
                                 @JsonProperty(value = "event_type", required = true) EventTypes eventType,
                                 @JsonProperty(value = "query_body") String query,
                                 @JsonProperty(value = "event_time", required = true) ZonedDateTime eventTime) {
}

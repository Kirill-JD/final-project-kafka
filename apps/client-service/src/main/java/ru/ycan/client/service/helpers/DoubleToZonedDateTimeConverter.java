package ru.ycan.client.service.helpers;

import jakarta.annotation.Nonnull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class DoubleToZonedDateTimeConverter implements Converter<Double, ZonedDateTime> {

    @Override
    public ZonedDateTime convert(@Nonnull Double source) {
        long epochMillis = (long) (source * 1000);
        return Instant.ofEpochMilli(epochMillis).atZone(ZoneId.systemDefault());
    }
}
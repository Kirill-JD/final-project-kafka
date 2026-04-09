package ru.ycan.analytics.service.config.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "analytics")
public record AnalyticsProperties(String column, List<String> values) {
}

package ru.ycan.analytics.service.config.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "hdfs")
public record HdfsProperties(String uri, String path) {
    public String fullPath() {
        return "%s%s".formatted(uri, path);
    }
}

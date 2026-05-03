package ru.ycan.analytics.service.config.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "hdfs")
public record HdfsProperties(String url, String path) {
}

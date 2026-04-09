package ru.ycan.shop.producer.config.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "files.input")
public record FileProperties(String path) {
}

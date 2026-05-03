package ru.ycan.shop.service.config.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "files.input")
public record FileProperties(String path) {
}

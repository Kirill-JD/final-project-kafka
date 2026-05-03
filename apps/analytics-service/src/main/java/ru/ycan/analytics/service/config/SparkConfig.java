package ru.ycan.analytics.service.config;

import org.apache.spark.sql.SparkSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ycan.analytics.service.config.props.HdfsProperties;

@Configuration
public class SparkConfig {
    @Bean
    SparkSession sparkSession(HdfsProperties properties) {
        return SparkSession.builder()
                           .appName("analytics-job")
                           .master("local[*]")
                           .config("spark.ui.enabled", "false")
                           .config("spark.hadoop.fs.defaultFS", properties.url())
                           .getOrCreate();
    }
}

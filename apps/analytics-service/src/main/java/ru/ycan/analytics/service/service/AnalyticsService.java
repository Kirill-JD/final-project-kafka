package ru.ycan.analytics.service.service;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Service;
import ru.ycan.analytics.service.config.props.AnalyticsProperties;
import ru.ycan.analytics.service.config.props.HdfsProperties;
import ru.ycan.analytics.service.config.props.KafkaTopicsProperties;

import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.apache.spark.sql.avro.functions.from_avro;
import static org.apache.spark.sql.functions.col;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final HdfsProperties hdfsProperties;
    private final KafkaTopicsProperties kafkaTopicsProperties;
    private final AnalyticsProperties analyticsProperties;
    private final SchemaRegistryService schemaRegistryService;
    private final SparkSession spark;
    private final KafkaProperties properties;
    @Value("${spring.kafka.bootstrap-servers}")
    String bootstrapServers;

    public void saveDataInHdfs() {
        Dataset<Row> kafkaDf = spark.read()
                                    .format("kafka")
                                    .option("kafka.bootstrap.servers", bootstrapServers)
                                    .option("subscribe", kafkaTopicsProperties.in())
                                    .option("startingOffsets", "earliest")
                                    .option("kafka.security.protocol", properties.getProperties().get("security.protocol"))
                                    .option("kafka.ssl.truststore.location", properties.getProperties().get("ssl.truststore.location"))
                                    .option("kafka.ssl.truststore.password", properties.getProperties().get("ssl.truststore.password"))
                                    .option("kafka.ssl.keystore.location", properties.getProperties().get("ssl.keystore.location"))
                                    .option("kafka.ssl.keystore.password", properties.getProperties().get("ssl.keystore.password"))
                                    .option("kafka.ssl.key.password", properties.getProperties().get("ssl.key.password"))
                                    .load();

        Dataset<Row> valueDf = kafkaDf.selectExpr("substring(value, 6) as value");
        String schema = schemaRegistryService.getLatestSchema(kafkaTopicsProperties.in());

        Dataset<Row> products = valueDf
                .select(from_avro(col("value"), schema).alias("data"))
                .select("data.*");

        products.write()
                .mode(SaveMode.Overwrite)
                .parquet(hdfsProperties.path());

        log.info("Данные записаны в HDFS: {}, {}, {}",
                 hdfsProperties.path(), products.count(), products.collectAsList());
    }

    public Dataset<Row> getRecommendationProducts() {
        Dataset<Row> hdfsData = spark.read().parquet(hdfsProperties.path());
        log.info("Получено '{}' записи из HDFS: {}", hdfsData.count(), hdfsData.collectAsList());

        var pattern = "(?i)(%s)".formatted(analyticsProperties.values().stream()
                                                              .map(Pattern::quote)
                                                              .collect(Collectors.joining("|")));

        var result = hdfsData.filter(col(analyticsProperties.column()).rlike(pattern));
        log.info("Отфильтрованных записей '{}' из HDFS: {}", result.count(), result.collectAsList());
        return result;
    }

    @PreDestroy
    public void close() {
        spark.stop();
        spark.close();
    }
}
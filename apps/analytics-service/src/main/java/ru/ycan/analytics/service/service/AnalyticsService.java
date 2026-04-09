package ru.ycan.analytics.service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.springframework.stereotype.Service;
import ru.ycan.analytics.service.config.props.AnalyticsProperties;
import ru.ycan.analytics.service.config.props.HdfsProperties;
import ru.ycan.analytics.service.config.props.KafkaProperties;

import static org.apache.spark.sql.functions.col;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final HdfsProperties hdfsProperties;
    private final KafkaProperties kafkaProperties;
    private final AnalyticsProperties analyticsProperties;

    public void analyticsProcess() {
        SparkSession spark = SparkSession.builder()
                                         .appName("analytics-job")
                                         .master("local[*]")
                                         .config("spark.ui.enabled", "false")
                                         .config("spark.hadoop.fs.defaultFS", hdfsProperties.uri())
                                         .getOrCreate();

        Dataset<Row> kafkaDf = spark.read()
                                    .format("kafka")
                                    .option("kafka.bootstrap.servers", kafkaProperties.bootstrapServers())
                                    .option("subscribe", kafkaProperties.topics().in())
                                    .option("startingOffsets", "earliest")
                                    .load();
        log.info("Получили '{}' записей из kafka: {}", kafkaDf.count(), kafkaDf.collectAsList());

        Dataset<Row> products = kafkaDf.selectExpr("CAST(value AS STRING) as value");
        String hdfsPath = hdfsProperties.path();

        products.write()
                .mode(SaveMode.Overwrite)
                .parquet(hdfsPath);
        log.info("Данные записаны в HDFS: {}, {}, {}", hdfsPath, products.count(), products.collectAsList());

        Dataset<Row> hdfsData = spark.read().parquet(hdfsPath);
        log.info("Получено '{}' записи из HDFS: {}", hdfsData.count(), hdfsData.collectAsList());

        Dataset<Row> result = hdfsData.filter(
                col("value").like("%%\"%s\":\"%s\"%%".formatted(analyticsProperties.column(),
                                                                analyticsProperties.values().get(0)))
        );
        log.info("Отфильтрованных записей '{}' из HDFS: {}", result.count(), result.collectAsList());

        result.selectExpr("CAST(value AS STRING) as value")
              .write()
              .format("kafka")
              .option("kafka.bootstrap.servers", kafkaProperties.bootstrapServers())
              .option("topic", kafkaProperties.topics().out())
              .save();

        log.info("Результат отправлен в Kafka");

        spark.stop();
    }
}
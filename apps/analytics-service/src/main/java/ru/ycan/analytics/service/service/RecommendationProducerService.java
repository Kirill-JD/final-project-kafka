package ru.ycan.analytics.service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.ycan.analytics.service.config.props.KafkaTopicsProperties;
import ru.ycan.libs.avro.schemas.ProductAvro;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendationProducerService {
    private static final SpecificDatumReader<ProductAvro> READER = new SpecificDatumReader<>(ProductAvro.class);

    private final KafkaTemplate<String, ProductAvro> kafkaTemplate;

    private final KafkaTopicsProperties kafkaTopicsProperties;

    public void sendRecords(Dataset<Row> df) {
        log.info("Начинаем отправку");
        df.toJSON().collectAsList().forEach(this::send);
        kafkaTemplate.flush();
    }

    private void send(String json) {
        log.info("Получили json: {}", json);
        try {
            Decoder decoder = DecoderFactory.get().jsonDecoder(ProductAvro.getClassSchema(), json);
            ProductAvro product = READER.read(null, decoder);
            log.info("Смаппили в ProductAvro: {}", product);
            kafkaTemplate.send(kafkaTopicsProperties.out(), product.getProductId(), product);
        } catch (Exception e) {
            log.error("Не удалось отправить продукт в топик рекомендаций: {}", json, e);
        }
    }
}

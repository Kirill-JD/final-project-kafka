package ru.ycan.shop.producer.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import ru.ycan.kafka.helper.pojo.Product;
import ru.ycan.shop.producer.exceptions.FileReaderException;
import ru.ycan.shop.producer.service.FileReadService;

import java.io.File;
import java.util.List;

import static ru.ycan.shop.producer.messages.Messages.*;

@Slf4j
@Service
public class FileReadServiceImpl implements FileReadService {
    private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

    @Value("${files.input.path}")
    private String path;

    @Override
    public List<Product> loadProductDataFromFile() {
        try {
            log.info(INFO_START_FILE_READ.getValue(), path);
            File file = new ClassPathResource(path).getFile();
            var products = MAPPER.readValue(file, new TypeReference<List<Product>>(){});
            log.info(INFO_SUCCESS_FILE_READ.getValue(), products.size());
            return products;

        } catch (Exception e) {
            log.error(ERROR_FILE_READ.getValue(), e);
            throw new FileReaderException(ERROR_FILE_READ.getValue(), e);
        }
    }
}

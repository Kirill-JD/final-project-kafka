package ru.ycan.shop.service.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.springframework.stereotype.Service;
import ru.ycan.libs.avro.schemas.ProductAvro;
import ru.ycan.shop.service.config.props.FileProperties;
import ru.ycan.shop.service.exceptions.FileReaderException;
import ru.ycan.shop.service.service.FileReadService;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static ru.ycan.shop.service.messages.Messages.ERROR_FILE_READ;
import static ru.ycan.shop.service.messages.Messages.INFO_START_FILES_READ;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileReadServiceImpl implements FileReadService {
    private static final SpecificDatumReader<ProductAvro> READER = new SpecificDatumReader<>(ProductAvro.class);

    private final FileProperties fileProperties;

    @Override
    public List<ProductAvro> loadProductDataFromFile() {
        log.info(INFO_START_FILES_READ.getValue(), fileProperties.path());
        List<ProductAvro> products = new ArrayList<>();

        try (var stream = Files.newDirectoryStream(getPath(), "*.json")) {
            for (Path path : stream) {
                String json = Files.readString(path);
                Decoder decoder = DecoderFactory.get().jsonDecoder(ProductAvro.getClassSchema(), json);
                ProductAvro product = READER.read(null, decoder);
                products.add(product);
            }
            return products;
        } catch (Exception e) {
            log.error(ERROR_FILE_READ.getValue(), e);
            throw new FileReaderException(ERROR_FILE_READ.getValue(), e);
        }
    }

    // лучше конечно по абсолютному пути итд, но в качестве учебного думаю ок (а то лишние телодвижения)
    private Path getPath() {
        try {
            URI uri = Objects.requireNonNull(FileReadServiceImpl.class.getClassLoader()
                                                                      .getResource(fileProperties.path())).toURI();
            return Paths.get(uri);
        } catch (URISyntaxException e) {
            log.error("Не удалось получить путь до директории");
            throw new IllegalStateException(e);
        }
    }
}

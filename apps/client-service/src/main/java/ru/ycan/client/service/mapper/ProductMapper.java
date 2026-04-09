package ru.ycan.client.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.ycan.client.service.model.Product;
import ru.ycan.libs.avro.schemas.ProductAvro;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {
    ProductAvro toProductResponse(Product product);

    default List<ProductAvro> toProductsResponse(List<Product> products) {
        return products.stream()
                       .map(this::toProductResponse)
                       .toList();
    }
}

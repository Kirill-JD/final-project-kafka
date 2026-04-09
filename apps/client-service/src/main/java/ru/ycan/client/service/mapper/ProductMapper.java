package ru.ycan.client.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.ycan.client.service.model.Product;
import ru.ycan.libs.common.pojo.ProductDto;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {
    ProductDto toProductResponse(Product product);

    default List<ProductDto> toProductsResponse(List<Product> products) {
        return products.stream()
                       .map(this::toProductResponse)
                       .toList();
    }
}

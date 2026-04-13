package ru.ycan.client.service.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@Document(indexName = "filter-product-topic")
public class Product {

    @Id
    @Field(name = "product_id", type = FieldType.Keyword)
    private String productId;
    @Field(type = FieldType.Keyword)
    private String name;
    private String description;
    private String category;
    private String brand;
    private String sku;
    @Field(name = "store_id")
    private String storeId;
    private String index;
    @Field(name = "created_at")
    private ZonedDateTime createdAt;
    @Field(name = "updated_at")
    private ZonedDateTime updatedAt;
    @Field(type = FieldType.Object)
    private Price price;
    @Field(type = FieldType.Object)
    private Stock stock;
    @Field(type = FieldType.Nested)
    private List<String> tags;
    @Field(type = FieldType.Nested)
    private List<Image> images;
    @Field(type = FieldType.Object)
    private Specification specifications;
}

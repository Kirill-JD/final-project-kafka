package ru.ycan.client.service.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Data
@Document(indexName = "recommendation")
public class Recommendation {
    @Id
    @Field(name = "user_id", type = FieldType.Keyword)
    private String userId;
    @Field(type = FieldType.Nested)
    private List<Product> products;
}

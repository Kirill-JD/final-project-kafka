package ru.ycan.libs.common.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

public record ProductDto(@JsonProperty("product_id") String productId,
                         @JsonProperty("name") String name,
                         @JsonProperty("description") String description,
                         @JsonProperty("price") Price price,
                         @JsonProperty("category") String category,
                         @JsonProperty("brand") String brand,
                         @JsonProperty("stock") Stock stock,
                         @JsonProperty("sku") String sku,
                         @JsonProperty("tags") List<String> tags,
                         @JsonProperty("images") List<Image> images,
                         @JsonProperty("specifications") Specifications specifications,
                         @JsonProperty("created_at")
                         @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
                         ZonedDateTime createdAt,
                         @JsonProperty("updated_at")
                         @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
                         ZonedDateTime updatedAt,
                         @JsonProperty("index") String index,
                         @JsonProperty("store_id") String storeId
) {

    public record Price(@JsonProperty("amount") BigDecimal amount,
                        @JsonProperty("currency") String currency) {}

    public record Stock(@JsonProperty("available") int available,
                        @JsonProperty("reserved") int reserved) {}

    public record Image(@JsonProperty("url") String url,
                        @JsonProperty("alt") String alt) {}

    public record Specifications(@JsonProperty("weight") String weight,
                                 @JsonProperty("dimensions") String dimensions,
                                 @JsonProperty("battery_life") String batteryLife,
                                 @JsonProperty("water_resistance") String waterResistance) {}
}
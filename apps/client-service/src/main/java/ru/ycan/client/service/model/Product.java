package ru.ycan.client.service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "product")
public class Product {

    @Id
    @Column(name = "product_id")
    private String productId;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String category;
    private String brand;
    private String sku;

    @Column(name = "store_id")
    private String storeId;

    @Column(name = "index_name")
    private String indexName;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "price_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "price_currency"))
    })
    private Price price;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "available", column = @Column(name = "stock_available")),
            @AttributeOverride(name = "reserved", column = @Column(name = "stock_reserved"))
    })
    private Stock stock;

    @ElementCollection
    @CollectionTable(name = "product_tags", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "tag")
    private List<String> tags;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> images;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private ProductSpecification specification;
}

package ru.ycan.client.service.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "product_image")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;
    private String alt;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}

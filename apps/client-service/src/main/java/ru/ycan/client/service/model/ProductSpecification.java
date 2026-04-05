package ru.ycan.client.service.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "product_specification")
public class ProductSpecification {

    @Id
    @Column(name = "product_id")
    private String productId;

    private String weight;
    private String dimensions;

    @Column(name = "battery_life")
    private String batteryLife;

    @Column(name = "water_resistance")
    private String waterResistance;

    @OneToOne
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;
}

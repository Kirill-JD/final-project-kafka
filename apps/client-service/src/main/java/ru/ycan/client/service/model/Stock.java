package ru.ycan.client.service.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Stock {
    private Integer available;
    private Integer reserved;
}

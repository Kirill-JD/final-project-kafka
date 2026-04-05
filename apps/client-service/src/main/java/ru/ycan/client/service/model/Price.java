package ru.ycan.client.service.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Price {
    private Double amount;
    private String currency;
}

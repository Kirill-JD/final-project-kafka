package ru.ycan.client.service.model;

import lombok.Data;

@Data
public class Stock {
    private Integer available;
    private Integer reserved;
}

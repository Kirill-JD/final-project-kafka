package ru.ycan.client.service.model;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;

@Data
public class Specification {
    private String weight;
    private String dimensions;
    @Field(name = "battery_life")
    private String batteryLife;
    @Field(name = "water_resistance")
    private String waterResistance;
}

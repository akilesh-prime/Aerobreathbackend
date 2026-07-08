package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetricDto {
    @NotNull
    private Long stationId;

    @NotNull
    private Integer aqi;

    private BigDecimal pm25;
    private BigDecimal pm10;
}

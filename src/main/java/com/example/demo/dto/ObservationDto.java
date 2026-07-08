package com.example.demo.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ObservationDto {
    @NotNull
    @Min(0)
    @Max(10)
    private Integer symptomScore;

    @Size(max = 500)
    private String notes;

    private String activityLevel;
}

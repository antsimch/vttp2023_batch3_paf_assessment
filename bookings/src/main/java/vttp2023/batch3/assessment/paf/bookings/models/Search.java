package vttp2023.batch3.assessment.paf.bookings.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Search {
    
    @NotNull(message = "Country field cannot be null")
    @NotEmpty(message = "Country field cannot be empty")
    private String country;

    @Min(value = 1, message = "Cannot be less than 1 person")
    @Max(value = 10, message = "Cannot be more than 10 persons")
    private Integer numberOfPerson;

    @Min(value = 1, message = "Price range cannot be less than 1")
    @Max(value = 10000, message = "Price range cannot exceed 10000")
    private Double priceRangeMin;

    @Min(value = 1, message = "Price range cannot be less than 1")
    @Max(value = 10000, message = "Price range cannot exceed 10000")
    private Double priceRangeMax;
}

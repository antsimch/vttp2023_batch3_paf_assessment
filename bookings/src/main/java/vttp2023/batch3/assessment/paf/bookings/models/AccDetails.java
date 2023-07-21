package vttp2023.batch3.assessment.paf.bookings.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccDetails {
    
    private String id;

    private String description;

    private List<String> address;

    private String image;

    private Double price;
    
    private String amenities;
}

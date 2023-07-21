package vttp2023.batch3.assessment.paf.bookings.models;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    private String id;
    
    private String name;

    private String email;

    private String accId;

    private LocalDate arrivalDate;

    private Integer duration;
}

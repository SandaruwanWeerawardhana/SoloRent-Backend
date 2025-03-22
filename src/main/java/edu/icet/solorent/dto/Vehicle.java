package edu.icet.solorent.dto;

import edu.icet.solorent.util.VehicleStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Vehicle {
    private Long vehicleID;

    @NotEmpty(message = "This should not be blank")
    private String brand;

    @NotEmpty(message = "This should not be blank")
    private String fuelType;

    @NotEmpty(message = "This should not be blank")
    private String registerNumber;

    @NotEmpty(message = "This should not be blank")
    private Double pricePerDay;

    @NotEmpty(message = "This should not be blank")
    @Enumerated(EnumType.STRING)
    private VehicleStatus status;

    @NotEmpty(message = "This should not be blank")
    private String imageURl;

    @NotEmpty(message = "This should not be blank")
    private String description;
}
